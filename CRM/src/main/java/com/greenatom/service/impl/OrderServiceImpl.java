package com.greenatom.service.impl;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.orderitem.OrderItemRequestDTO;
import com.greenatom.domain.dto.order.OrderRequestDTO;
import com.greenatom.domain.dto.order.OrderResponseDTO;
import com.greenatom.domain.dto.order.OrderSearchCriteria;
import com.greenatom.domain.entity.*;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.domain.enums.PreparingOrderStatus;
import com.greenatom.domain.mapper.OrderMapper;
import com.greenatom.exception.FileException;
import com.greenatom.exception.OrderException;
import com.greenatom.repository.*;
import com.greenatom.repository.criteria.OrderCriteriaRepository;
import com.greenatom.service.OrderService;
import com.greenatom.utils.generator.request.OrderGenerator;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * OrderServiceImpl является сервисом для работы с запросами. Он использует базы данных для доступа к информации
 * о запросах, клиентах и сотрудниках, преобразует эту информацию в формат DTO и возвращает списки запросов
 * или конкретные запросы по их ID.
 *
 * <p>Сохранение заявки: Метод save принимает объект OrderDTO, содержащий id клитента, id
 * сотрудника,
 * ссылку на дерикторию с документами заявки, дату создания, и статус. В методе происходит сохранение записи
 * в базу данных, а также сохранение docx документа в папку в документами заявки.
 *
 * @author Максим Быков, Даниил Змаев
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final PreparingOrderRepository preparingOrderRepository;

    private final Environment env;
    private final OrderMapper orderMapper;

    private final JavaMailSender mailSender;

    private final MinioClient minioClient;

    private final OrderCriteriaRepository orderCriteriaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> findAll(EntityPage entityPage,
                                          OrderSearchCriteria employeeSearchCriteria) {
        return orderCriteriaRepository.findAllWithFilters(entityPage,
                employeeSearchCriteria).map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findByPaginationAndFilters(PageRequest pageRequest, String orderStatus, String linkToFolder) {
        return orderRepository
                .findByOrderStatusAndLinkToFolder(pageRequest, orderStatus, linkToFolder)
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO findOne(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO createDraft(String username, OrderRequestDTO orderRequestDTO) {
        List<OrderItemRequestDTO> orderItemList = orderRequestDTO.getOrderItemList();
        Order order = createDraftOrder(username, orderRequestDTO);
        for (OrderItemRequestDTO orderItem : orderItemList) {
            Product currProduct = productRepository
                    .findById(orderItem.getProductId())
                    .orElseThrow(OrderException.CODE.NO_SUCH_PRODUCT::get);

            if (currProduct.getStorageAmount() <= orderItem.getOrderAmount() ||
            orderItem.getOrderAmount() < 1) {
                throw OrderException.CODE.INVALID_ORDER.get();
            }

            currProduct.setStorageAmount(currProduct.getStorageAmount() - orderItem.getOrderAmount());
            orderItemRepository.save(OrderItem.builder()
                    .product(currProduct)
                    .orderAmount(orderItem.getOrderAmount())
                    .cost(currProduct.getCost())
                    .unit(currProduct.getUnit())
                    .name(currProduct.getProductName())
                    .order(order)
                    .build());

        }
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO finishOrder(String username, Long orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (!Objects.equals(order.getOrderStatus(), OrderStatus.SIGNED_BY_CLIENT)) {
            throw OrderException.CODE.INVALID_STATUS.get();
        }
        if (!Objects.equals(order.getEmployee().getUsername(), username)) {
            throw OrderException.CODE.NOT_PERMIT.get(username);
        }
        sendOrderToClient(order);
        order.setOrderStatus(OrderStatus.FINISHED);
        return orderMapper.toDto(order);
    }

    private void sendOrderToClient(Order order) {
        File file = new File(order.getId() + ".docx");
        Client client = order.getClient();
        String toAddress = client.getEmail();
        String senderName = "Green Atom";
        String subject = "Ваш заказ";
        String content = "Дорогой [[name]],<br>"
                + "Ваш заказ готов, чек в приложенном файле<br>"
                + "Спасибо за покупку,<br>"
                + "Ваш Green Atom.";
        content = content.replace("[[name]]", client.getFullName());
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket("documents")
                    .object(order.getId() + "/" + "signed_by_client.docx")
                    .build());
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (MinioException e) {
            throw FileException.CODE.MINIO.get(e.getMessage());
        } catch (InvalidKeyException e) {
            throw FileException.CODE.INVALID_KEY.get(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw FileException.CODE.ALGORITHM_NOT_FOUND.get(e.getMessage());
        } catch (IOException e) {
            throw FileException.CODE.IO.get(e.getMessage());
        }
        try {
            String fromAddress = env.getProperty("mail_address");
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(Objects.requireNonNull(fromAddress), senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.addAttachment("Заказ.docx", file);
            helper.setText(content, true);
        } catch (MessagingException | IOException e) {
            throw new MailSendException("Couldn't send email to address: " + toAddress, e);
        }
        mailSender.send(message);

    }

    private Order createDraftOrder(String username, OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        Client client = clientRepository
                .findById(orderRequestDTO.getClientId())
                .orElseThrow(OrderException.CODE.NO_SUCH_CLIENT::get);
        Employee employee = employeeRepository
                .findByUsername(username)
                .orElseThrow(() -> OrderException.CODE.NO_SUCH_EMPLOYEE.get(username));
        order.setClient(client);
        order.setEmployee(employee);
        order.setOrderDate(Instant.now());
        order.setOrderStatus(OrderStatus.DRAFT);
        order = orderRepository.save(order);
        order.setLinkToFolder(String.valueOf(order.getId()));
        return order;
    }

    @Override
    @Transactional
    public void generateOrder(String username, Long orderId) {
        OrderGenerator orderGenerator = new OrderGenerator();
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (!order.getOrderStatus().equals(OrderStatus.DRAFT)) {
            throw OrderException.CODE.CANNOT_ASSIGN_ORDER.get();
        }
        if (!Objects.equals(order.getEmployee().getUsername(), username)) {
            throw OrderException.CODE.NOT_PERMIT.get(username);
        }
        String filename = "Order_" + order.getId();
        byte[] doc = orderGenerator.processGeneration(
                order.getOrderItems(),
                order.getClient(),
                order.getEmployee(),
                filename + ".docx");
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("documents")
                            .object(order.getId() + "/" + "assigned_by_employee.docx")
                            .stream(new ByteArrayInputStream(doc), doc.length, -1)
                            .build());
        } catch (MinioException e) {
            throw FileException.CODE.MINIO.get(e.getMessage());
        } catch (InvalidKeyException e) {
            throw FileException.CODE.INVALID_KEY.get(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw FileException.CODE.ALGORITHM_NOT_FOUND.get(e.getMessage());
        } catch (IOException e) {
            throw FileException.CODE.IO.get(e.getMessage());
        }
        preparingOrderRepository.save(PreparingOrder.builder()
                .order(order)
                .preparingOrderStatus(PreparingOrderStatus.WAITING_FOR_PREPARING)
                .startTime(Instant.now())
                .build());
        order.setOrderStatus(OrderStatus.IN_PROCESS);
    }

    @Override
    @Transactional
    public OrderResponseDTO save(OrderResponseDTO orderResponseDTO) {
        Order order = orderMapper.toEntity(orderResponseDTO);
        order.setClient(clientRepository.findById(
                        orderResponseDTO.getClient().getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get));
        order.setEmployee(employeeRepository.findById(
                        orderResponseDTO.getEmployee().getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_EMPLOYEE::get));
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrder(OrderResponseDTO order, Long id) {
        return orderRepository
                .findById(id)
                .map(existingEvent -> {
                    orderMapper.partialUpdate(existingEvent, order);
                    return existingEvent;
                })
                .map(orderRepository::save)
                .map(orderMapper::toDto)
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (Objects.equals(order.getOrderStatus(), OrderStatus.DRAFT)) {
            orderRepository.delete(order);
        } else {
            throw OrderException.CODE.CANNOT_DELETE_ORDER.get();
        }
    }
}