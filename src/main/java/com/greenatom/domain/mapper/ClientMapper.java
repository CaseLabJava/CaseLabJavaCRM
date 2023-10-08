package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.ClientDTO;
import com.greenatom.domain.entity.Client;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Client} and its DTO called {@link ClientDTO}.
 */

@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    ClientDTO toDto(Client s);

    Client toEntity(ClientDTO s);
}
