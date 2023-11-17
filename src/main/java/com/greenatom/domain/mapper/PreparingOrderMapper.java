package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.preparing_order.PreparingOrderRequestDTO;
import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.domain.entity.PreparingOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper for the entity {@link PreparingOrder} and its DTO called {@link PreparingOrderResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface PreparingOrderMapper extends EntityMapper<PreparingOrderResponseDTO, PreparingOrder> {
    @Mapping(target = "orderId", source = "s.order.id")
    PreparingOrderResponseDTO toDto(PreparingOrder s);

    PreparingOrder toEntity(PreparingOrderResponseDTO s);

    PreparingOrder toEntity(PreparingOrderRequestDTO s);

    @Mapping(target = "orderId", source = "page.order.id")
    List<PreparingOrderResponseDTO> toDto(Page<PreparingOrder> all);
}
