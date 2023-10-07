package com.greenatom.domain.mapper;
import com.greenatom.domain.dto.SuccessfulWorkDTO;
import com.greenatom.domain.entity.SuccessfulWork;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link SuccessfulWork} and its DTO called
 * {@link SuccessfulWorkDTO}.
 */

@Mapper(componentModel = "spring")
public interface SuccesfulWorkMapper extends EntityMapper<SuccessfulWorkDTO,
        SuccessfulWork> {
    SuccessfulWorkDTO toDto(SuccessfulWork s);

    SuccessfulWork toEntity(SuccessfulWorkDTO s);

}