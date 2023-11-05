package com.greenatom.service.impl;

import com.greenatom.domain.dto.item.OrderItemRequestDTO;
import com.greenatom.domain.dto.order.GenerateOrderRequestDTO;
import com.greenatom.domain.dto.order.OrderRequestDTO;
import com.greenatom.domain.dto.order.OrderResponseDTO;
import com.greenatom.domain.dto.order.UploadDocumentRequestDTO;
import com.greenatom.domain.entity.*;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.domain.enums.PreparingOrderStatus;
import com.greenatom.domain.mapper.OrderMapper;
import com.greenatom.repository.*;
import com.greenatom.service.OrderService;
import com.greenatom.utils.exception.OrderException;
import com.greenatom.utils.generator.request.OrderGenerator;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
 * @author Максим Быков, Даниил Змаев
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final PreparingOrderRepository preparingOrderRepository;

    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponseDTO> findAll(Integer pagePosition, Integer pageLength,
                                          Long id) {
        return orderMapper.toDto(orderRepository.findAllByEmployeeId(id,
                PageRequest.of(pagePosition, pageLength)));
    }

    @Override
    public List<OrderResponseDTO> findByPaginationAndFilters(PageRequest pageRequest, String orderStatus, String linkToFolder) {
        return orderRepository
                .findByOrderStatusAndLinkToFolder(pageRequest, orderStatus, linkToFolder)
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDTO findOne(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderResponseDTO createDraft(OrderRequestDTO orderRequestDTO) {
        List<OrderItemRequestDTO> orderItemList = orderRequestDTO.getOrderItemList();
        Order order = createDraftOrder(orderRequestDTO);
        for (OrderItemRequestDTO orderItem: orderItemList) {
            Product currProduct = productRepository
                    .findById(orderItem.getProductId())
                    .orElseThrow(OrderException.CODE.NO_SUCH_PRODUCT::get);
            if (currProduct.getStorageAmount() > orderItem.getOrderAmount()) {
                currProduct.setStorageAmount(currProduct.getStorageAmount() - orderItem.getOrderAmount());
                orderItemRepository.save(OrderItem.builder()
                        .product(currProduct)
                        .orderAmount(orderItem.getOrderAmount())
                        .cost(currProduct.getCost())
                        .unit(currProduct.getUnit())
                        .name(currProduct.getProductName())
                        .order(order)
                        .build());
            } else throw OrderException.CODE.INVALID_ORDER.get();
        }
        return orderMapper.toDto(order);
    }

    @Override
    public OrderResponseDTO finishOrder(Long id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (Objects.equals(order.getOrderStatus(), OrderStatus.SIGNED_BY_CLIENT)) {
            order.setOrderStatus(OrderStatus.FINISHED);
        } else {
            throw OrderException.CODE.INVALID_STATUS.get();
        }
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }


    private Order createDraftOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        Client client = clientRepository
                .findById(orderRequestDTO.getClientId())
                .orElseThrow(OrderException.CODE.NO_SUCH_CLIENT::get);
        Employee employee = employeeRepository
                .findById(orderRequestDTO.getEmployeeId())
                .orElseThrow(OrderException.CODE.NO_SUCH_EMPLOYEE::get);
        order.setClient(client);
        order.setEmployee(employee);
        order.setOrderDate(Instant.now());
        order.setOrderStatus(OrderStatus.DRAFT);
        // TODO: Поменять, когда будет понятно на что
        order.setLinkToFolder("LINK_TO_FOLDER_SAMPLE");
        order = orderRepository.save(order);
        return order;
    }

    @Override
    public void generateOrder(GenerateOrderRequestDTO request) {
        OrderGenerator orderGenerator = new OrderGenerator();
        Order order = orderRepository
                .findById(request.getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (order.getOrderStatus().equals(OrderStatus.DRAFT)) {
            String filename = "Order_" + order.getId();
            orderGenerator.processGeneration(
                    order.getOrderItems(),
                    order.getClient(),
                    order.getEmployee(),
                    filename + ".docx");
            generatePreparingOrder(order);
            order.setOrderStatus(OrderStatus.IN_PROCESS);
        } else {
            throw OrderException.CODE.CANNOT_ASSIGN_ORDER.get();
        }
    }

    @Override
    public void generatePreparingOrder(Order order) {
        preparingOrderRepository.save(new PreparingOrder().builder()
                .order(order)
                .preparingOrderStatus(PreparingOrderStatus.WAITING_FOR_PREPARING)
                .startTime(Instant.now())
                .build());
    }

    @Override
    public OrderResponseDTO save(OrderResponseDTO orderResponseDTO) {
        Order order = orderMapper.toEntity(orderResponseDTO);
        order.setClient(clientRepository.findById(
                orderResponseDTO.getClient().getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get));
        order.setEmployee(employeeRepository.findById(
                orderResponseDTO.getEmployee().getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_EMPLOYEE::get));
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderResponseDTO updateOrder(OrderResponseDTO order) {
        return orderRepository
                .findById(order.getId())
                .map(existingEvent -> {
                    orderMapper.partialUpdate(existingEvent, order);

                    return existingEvent;
                })
                .map(orderRepository::save)
                .map(orderMapper::toDto)
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (Objects.equals(order.getOrderStatus(), OrderStatus.DRAFT)) {
            orderRepository.delete(order);
        } else {
            throw OrderException.CODE.CANNOT_DELETE_ORDER.get();
        }
    }

    //в finishOrder буду конвертировать документ в PDF и отправлять клиенту на почту
    public void convertToPDF(String linkToFolder, String localPdfPath) {
        try (InputStream doc = new FileInputStream(linkToFolder);
             XWPFDocument document = new XWPFDocument(doc)) {
            PdfOptions options = PdfOptions.create();
            OutputStream out = new FileOutputStream(localPdfPath);
            PdfConverter.getInstance().convert(document, out, options);
        } catch (IOException ex) {
            log.error("Couldn't convert file");
        }
    }

    //Загрузка подписанного документа
    @Override
    public void upload(UploadDocumentRequestDTO uploadDocumentRequestDTO) {
        MultipartFile file = uploadDocumentRequestDTO.getFile();
        if (!file.isEmpty()) {
            try {
                String projectRoot = System.getProperty("user.dir");
                String uploadDir = projectRoot + "/Documents/UploadDoc";
                String fileName = cleanFileName(Objects.requireNonNull(file.getOriginalFilename()));

                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    if (uploadPath.mkdirs()) {
                        log.info("Dir created");
                    } else {
                        log.error("Error with dir creation");
                    }
                }

                File targetFile = new File(uploadPath, fileName);
                try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                    fos.write(file.getBytes());
                }
                uploadDocumentRequestDTO.setLinkToFolder(targetFile.getAbsolutePath());
                log.info("The file has been successfully uploaded. File name: " + fileName + ", Path: "
                        + targetFile.getAbsolutePath());
            } catch (IOException e) {
                log.error("Error uploading file: " + e.getMessage());
            }
        } else {
            log.error("File is empty, download failed.");
        }
        updateStatus(uploadDocumentRequestDTO);
    }


    //Обновляем статус в заявке на SIGNED_BY_CLIENT
    private void updateStatus(UploadDocumentRequestDTO uploadDocumentRequestDTO) {
        Order order = orderRepository
                .findById(uploadDocumentRequestDTO.getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (order.getOrderStatus().equals(OrderStatus.SIGNED_BY_EMPLOYEE)) {

            order.setOrderStatus(OrderStatus.SIGNED_BY_CLIENT);
        } else {
            throw OrderException.CODE.CANNOT_ASSIGN_ORDER.get();
        }
        updatePath(uploadDocumentRequestDTO);
    }

    private void updatePath(UploadDocumentRequestDTO uploadDocumentRequestDTO) {
        log.debug("Order to update link_to_folder : {}", uploadDocumentRequestDTO);

        Long orderId = uploadDocumentRequestDTO.getId();
        String linkToFolder = uploadDocumentRequestDTO.getLinkToFolder();

        orderRepository.updateLinkToFolder(orderId, linkToFolder);
    }

    private String cleanFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9_-]", "");
    }
}