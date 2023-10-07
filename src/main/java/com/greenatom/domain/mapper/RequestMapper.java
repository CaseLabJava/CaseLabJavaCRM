package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.RequestDTO;
import com.greenatom.domain.entity.Request;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Request} and its DTO called {@link RequestDTO}.
 */

@Mapper(componentModel = "spring")
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {
    RequestDTO toDto(Request s);

    Request toEntity(RequestDTO s);
}
