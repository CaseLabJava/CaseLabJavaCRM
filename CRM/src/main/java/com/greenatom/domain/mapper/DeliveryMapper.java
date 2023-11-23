package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.delivery.DeliveryRequestDTO;
import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper for the entity {@link Delivery} and its DTO called {@link DeliveryResponseDTO}.
 */

@Mapper(componentModel = "spring", uses = {CourierMapper.class, OrderMapper.class})
public interface DeliveryMapper extends EntityMapper<DeliveryResponseDTO, Delivery> {

    @Mapping(target = "orderId", source = "s.order.id")
    @Mapping(target = "courierId", source = "s.courier.id")
    DeliveryResponseDTO toDto(Delivery s);

    Delivery toEntity(DeliveryResponseDTO s);

    Delivery toEntity(DeliveryRequestDTO s);

    List<DeliveryResponseDTO> toDto(Page<Delivery> page);
}
