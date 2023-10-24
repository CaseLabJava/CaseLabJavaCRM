package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.OrderDTO;
import com.greenatom.domain.entity.Order;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Order} and its DTO called {@link OrderDTO}.
 */

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    OrderDTO toDto(Order s);

    Order toEntity(OrderDTO s);
}
