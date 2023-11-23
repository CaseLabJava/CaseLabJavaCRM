package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.domain.entity.Client;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper for the entity {@link Client} and its DTO called {@link ClientResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientResponseDTO, Client> {
    ClientResponseDTO toDto(Client s);

    Client toEntity(ClientResponseDTO s);

    Client toEntity(ClientRequestDTO s);

    ClientResponseDTO toResponse(ClientRequestDTO s);

    List<ClientResponseDTO> toDto(Page<Client> all);
}
