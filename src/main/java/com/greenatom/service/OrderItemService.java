package com.greenatom.service;

import com.greenatom.domain.dto.OrderItemDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {

    @Transactional(readOnly = true)
    List<OrderItemDTO> findAll();

    @Transactional(readOnly = true)
    Optional<OrderItemDTO> findOne(Long id);

    @Transactional
    OrderItemDTO save(OrderItemDTO cartProduct);

    @Transactional
    OrderItemDTO updateCartProduct(OrderItemDTO cartProduct);

    @Transactional
    void deleteCartProduct(Long id);
}
