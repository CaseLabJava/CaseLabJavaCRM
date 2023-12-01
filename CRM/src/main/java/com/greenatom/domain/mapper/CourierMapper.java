package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.courier.CourierRequestDTO;
import com.greenatom.domain.dto.courier.CourierResponseDTO;
import com.greenatom.domain.entity.Courier;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Courier} and its DTO called {@link CourierResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface CourierMapper extends EntityMapper<CourierResponseDTO, Courier> {
    CourierResponseDTO toDto(Courier s);

    Courier toEntity(CourierResponseDTO s);

    Courier toEntity(CourierRequestDTO s);

    CourierResponseDTO toResponse(CourierRequestDTO s);
}
