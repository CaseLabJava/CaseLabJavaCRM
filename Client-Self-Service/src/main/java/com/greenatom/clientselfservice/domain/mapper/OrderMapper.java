package com.greenatom.clientselfservice.domain.mapper;

import com.greenatom.clientselfservice.domain.dto.order.OrderResponseDTO;
import com.greenatom.clientselfservice.domain.entity.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderMapper extends EntityMapper<OrderResponseDTO, Order>{
    OrderResponseDTO toDto(Order s);

    Order toEntity(OrderResponseDTO s);

    List<OrderResponseDTO> toDto(Page<Order> all);
}
