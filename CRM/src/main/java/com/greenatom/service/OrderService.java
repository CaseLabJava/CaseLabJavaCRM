package com.greenatom.service;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.order.OrderRequestDTO;
import com.greenatom.domain.dto.order.OrderResponseDTO;
import com.greenatom.domain.dto.order.OrderSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.security.Principal;
import java.util.List;

public interface OrderService {


    Page<OrderResponseDTO> findAll(EntityPage entityPage,
                                   OrderSearchCriteria employeeSearchCriteria);

    List<OrderResponseDTO> findByPaginationAndFilters(PageRequest pageRequest, String orderStatus, String linkToFolder);

    OrderResponseDTO findOne(Long id);

    OrderResponseDTO save(OrderResponseDTO orderResponseDTO);

    void generateOrder(String username, Long orderId);

    OrderResponseDTO updateOrder(OrderResponseDTO orderResponseDTO, Long id);

    void deleteOrder(Long id);

    OrderResponseDTO createDraft(String username, OrderRequestDTO orderRequestDTO);

    OrderResponseDTO finishOrder(String username, Long orderId);
}
