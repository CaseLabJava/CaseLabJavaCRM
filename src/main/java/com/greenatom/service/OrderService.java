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
    OrderDTO save(OrderDTO request);

    @Transactional
    OrderDTO updateOrder(OrderDTO request);

    @Transactional
    void deleteOrder(Long id);
}
