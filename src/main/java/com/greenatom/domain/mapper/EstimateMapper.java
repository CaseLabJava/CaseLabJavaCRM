package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.EstimateDTO;
import com.greenatom.domain.entity.Estimate;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Estimate} and its DTO called {@link EstimateDTO}.
 */

@Mapper(componentModel = "spring")
public interface EstimateMapper extends EntityMapper<EstimateDTO, Estimate> {
    EstimateDTO toDto(Estimate s);

    Estimate toEntity(EstimateDTO s);

}
