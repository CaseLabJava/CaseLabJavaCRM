package com.greenatom.service;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.orderitem.OrderItemResponseDTO;
import com.greenatom.domain.dto.orderitem.OrderItemSearchCriteria;
import org.springframework.data.domain.Page;

public interface OrderItemService {

    Page<OrderItemResponseDTO> findAll(EntityPage entityPage, OrderItemSearchCriteria orderItemSearchCriteria);

    OrderItemResponseDTO findOne(Long id);

    OrderItemResponseDTO updateCartProduct(OrderItemResponseDTO cartProduct);

    void deleteCartProduct(Long id);
}
