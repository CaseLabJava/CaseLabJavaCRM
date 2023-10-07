package com.greenatom.domain.mapper;
import com.greenatom.domain.dto.SupplyDTO;
import com.greenatom.domain.entity.Supply;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Supply} and its DTO called {@link SupplyDTO}.
 */

@Mapper(componentModel = "spring")
public interface SupplyMapper extends EntityMapper<SupplyDTO, Supply> {
    SupplyDTO toDto(Supply s);

    Supply toEntity(SupplyDTO s);

}
