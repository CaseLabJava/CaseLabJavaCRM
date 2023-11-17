package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.order.OrderResponseDTO;
import com.greenatom.domain.entity.Order;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper for the entity {@link Order} and its DTO called {@link OrderResponseDTO}.
 */

@Mapper(componentModel = "spring", uses = {ClientMapper.class, EmployeeMapper.class})
public interface OrderMapper extends EntityMapper<OrderResponseDTO, Order> {
    OrderResponseDTO toDto(Order s);

    Order toEntity(OrderResponseDTO s);

    List<OrderResponseDTO> toDto(Page<Order> all);
}
