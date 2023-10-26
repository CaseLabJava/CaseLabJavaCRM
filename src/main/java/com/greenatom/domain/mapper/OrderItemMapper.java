package com.greenatom.domain.mapper;
import com.greenatom.domain.dto.item.OrderItemDTO;
import com.greenatom.domain.dto.item.OrderItemRequest;
import com.greenatom.domain.entity.OrderItem;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link OrderItem} and its DTO called {@link OrderItemDTO}.
 */

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {
    OrderItemDTO toDto(OrderItem s);

    OrderItem toEntity(OrderItemDTO s);

    OrderItem toEntity(OrderItemRequest s);
}
