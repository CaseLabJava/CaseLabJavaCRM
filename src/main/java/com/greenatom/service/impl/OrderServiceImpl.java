package com.greenatom.service.impl;

import com.greenatom.domain.dto.item.OrderItemRequest;
import com.greenatom.domain.dto.order.GenerateOrderRequest;
import com.greenatom.domain.dto.order.OrderDTO;
import com.greenatom.domain.dto.order.OrderRequest;
import com.greenatom.domain.entity.*;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.domain.mapper.OrderMapper;
import com.greenatom.repository.*;
import com.greenatom.service.OrderService;
import com.greenatom.utils.date.DateTimeUtils;
import com.greenatom.utils.exception.OrderException;
import com.greenatom.utils.generator.request.OrderGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
 * @autor Максим Быков, Даниил Змаев
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    @Override
    public List<OrderDTO> findAll() {
        log.debug("Order to get all Orders");
        return orderMapper.toDto(orderRepository.findAll());
    }

    @Override
    public OrderDTO findOne(Long id) {
        log.debug("Order to get Order : {}", id);
        Order order = orderRepository.findById(id).orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDTO createDraft(OrderRequest orderRequest) {
        List<OrderItemRequest> orderItemList = orderRequest.getOrderItemList();
        Order order = createDraftOrder(orderRequest);
        for (OrderItemRequest orderItem: orderItemList) {
            Product currProduct = productRepository
                    .findById(orderItem.getProductId())
                    .orElseThrow(OrderException.CODE.NO_SUCH_PRODUCT::get);
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

    private Order createDraftOrder(OrderRequest orderRequest) {
        Order order = new Order();
        Client client = clientRepository
                .findById(orderRequest.getClientId())
                .orElseThrow(OrderException.CODE.NO_SUCH_CLIENT::get);
        Employee employee = employeeRepository
                .findById(orderRequest.getEmployeeId())
                .orElseThrow(OrderException.CODE.NO_SUCH_EMPLOYEE::get);
        order.setClient(client);
        order.setEmployee(employee);
        order.setOrderDate(DateTimeUtils.getTodayDate());
        order.setOrderStatus(OrderStatus.DRAFT.name());
        // TODO: Поменять, когда будет понятно на что
        order.setLinkToFolder("LINK_TO_FOLDER_SAMPLE");
        order = orderRepository.save(order);
        return order;
    }

    @Override
    public void generateOrder(GenerateOrderRequest request) {
        OrderGenerator orderGenerator = new OrderGenerator();
        Order order = orderRepository
                .findById(request.getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (order.getOrderStatus().equals(OrderStatus.DRAFT.name())) {
            orderGenerator.processGeneration(
                    order.getOrderItems(),
                    order.getClient(),
                    order.getEmployee(),
                    "test.docx");
            order.setOrderStatus(OrderStatus.ASSIGNED.name());
        } else {
            throw OrderException.CODE.CANNOT_ASSIGN_ORDER.get();
        }
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Order to save order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order.setClient(clientRepository.findById(orderDTO.getClient().getId()).orElseThrow());
        order.setEmployee(employeeRepository.findById(orderDTO.getEmployee().getId()).orElseThrow());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO order) {
        log.debug("Order to partially update Order : {}", order);
        return orderRepository
                .findById(order.getId())
                .map(existingEvent -> {
                    orderMapper.partialUpdate(existingEvent, order);

                    return existingEvent;
                })
                .map(orderRepository::save)
                .map(orderMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (Objects.equals(order.getOrderStatus(), OrderStatus.DRAFT.name())) {
            orderRepository.delete(order);
        } else {
            throw OrderException.CODE.CANNOT_DELETE_ORDER.get();
        }
    }
}
