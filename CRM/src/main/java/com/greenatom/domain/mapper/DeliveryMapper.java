package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.delivery.DeliveryRequestDTO;
import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.entity.Delivery;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Delivery} and its DTO called {@link DeliveryResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface DeliveryMapper extends EntityMapper<DeliveryResponseDTO, Delivery> {
    DeliveryResponseDTO toDto(Delivery s);

    Delivery toEntity(DeliveryResponseDTO s);

    Delivery toEntity(DeliveryRequestDTO s);

    DeliveryResponseDTO toResponse(DeliveryRequestDTO s);
}
