package com.greenatom.service;

import com.greenatom.domain.dto.OrderDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    @Transactional(readOnly = true)
    List<OrderDTO> findAll();

    @Transactional(readOnly = true)
    Optional<OrderDTO> findOne(Long id);

    @Transactional
    OrderDTO save(OrderDTO orderDTO);

    @Transactional
    OrderDTO updateOrder(OrderDTO orderDTO);

    @Transactional
    void deleteOrder(Long id);
}
