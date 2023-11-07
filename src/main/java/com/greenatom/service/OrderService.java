package com.greenatom.service;

import com.greenatom.domain.dto.order.GenerateOrderRequestDTO;
import com.greenatom.domain.dto.order.OrderRequestDTO;
import com.greenatom.domain.dto.order.OrderResponseDTO;
import com.greenatom.domain.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> findAll(Integer pagePosition, Integer pageLength, Long id);

    @Transactional(readOnly = true)
    List<OrderResponseDTO> findByPaginationAndFilters(PageRequest pageRequest, String orderStatus, String linkToFolder);

    @Transactional(readOnly = true)
    OrderResponseDTO findOne(Long id);

    OrderResponseDTO save(OrderResponseDTO orderResponseDTO);

    @Transactional
    void generateOrder(GenerateOrderRequestDTO request);

    OrderResponseDTO updateOrder(OrderResponseDTO orderResponseDTO);

    void deleteOrder(Long id);

    OrderResponseDTO createDraft(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO finishOrder(Long id);

    void generatePreparingOrder(Order order);
}
