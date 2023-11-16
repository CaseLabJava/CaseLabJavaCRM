package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.preparing_order.PreparingOrderRequestDTO;
import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.domain.entity.PreparingOrder;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link PreparingOrder} and its DTO called {@link PreparingOrderResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface PreparingOrderMapper extends EntityMapper<PreparingOrderResponseDTO, PreparingOrder> {
    PreparingOrderResponseDTO toDto(PreparingOrder s);

    PreparingOrder toEntity(PreparingOrderResponseDTO s);

    PreparingOrder toEntity(PreparingOrderRequestDTO s);

    PreparingOrderResponseDTO toResponse(PreparingOrderRequestDTO s);
}
