package com.greenatom.service;

import com.greenatom.domain.dto.item.OrderItemResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderItemService {

    @Transactional(readOnly = true)
    List<OrderItemResponseDTO> findAll();

    @Transactional(readOnly = true)
    OrderItemResponseDTO findOne(Long id);

    @Transactional
    OrderItemResponseDTO updateCartProduct(OrderItemResponseDTO cartProduct);

    @Transactional
    void deleteCartProduct(Long id);
}
