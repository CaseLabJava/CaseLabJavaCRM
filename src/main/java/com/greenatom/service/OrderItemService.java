package com.greenatom.service;

import com.greenatom.domain.dto.item.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService {

    List<OrderItemResponseDTO> findAll();

    OrderItemResponseDTO findOne(Long id);

    OrderItemResponseDTO updateCartProduct(OrderItemResponseDTO cartProduct);

    void deleteCartProduct(Long id);
}
