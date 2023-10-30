package com.greenatom.service;

import com.greenatom.domain.dto.order.GenerateOrderRequest;
import com.greenatom.domain.dto.order.OrderDTO;
import com.greenatom.domain.dto.order.OrderRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    @Transactional(readOnly = true)
    List<OrderDTO> findAll(Integer pagePosition, Integer pageLength, Long id);

    @Transactional(readOnly = true)
    List<OrderDTO> findByPaginationAndFilters(PageRequest pageRequest, String orderStatus, String linkToFolder);

    @Transactional(readOnly = true)
    OrderDTO findOne(Long id);

    @Transactional
    OrderDTO save(OrderDTO orderDTO);

    @Transactional
    void generateOrder(GenerateOrderRequest request);

    @Transactional
    OrderDTO updateOrder(OrderDTO orderDTO);

    @Transactional
    void deleteOrder(Long id);

    OrderDTO createDraft(OrderRequest orderRequest);

    OrderDTO finishOrder(Long id);
}
