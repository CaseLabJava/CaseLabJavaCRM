package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.orderitem.OrderItemRequestDTO;
import com.greenatom.domain.dto.orderitem.OrderItemResponseDTO;
import com.greenatom.domain.entity.OrderItem;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link OrderItem} and its DTO called {@link OrderItemResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemResponseDTO, OrderItem> {
    OrderItemResponseDTO toDto(OrderItem s);

    OrderItem toEntity(OrderItemResponseDTO s);

    OrderItem toEntity(OrderItemRequestDTO s);
}
