package com.greenatom.service;

import com.greenatom.domain.dto.item.OrderItemDTO;
import com.greenatom.domain.dto.item.OrderItemRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {

    @Transactional(readOnly = true)
    List<OrderItemDTO> findAll();

    @Transactional(readOnly = true)
    Optional<OrderItemDTO> findOne(Long id);

    @Transactional
    void save(OrderItemRequest orderItem);

    @Transactional
    OrderItemDTO updateCartProduct(OrderItemDTO cartProduct);

    @Transactional
    void deleteCartProduct(Long id);
}
