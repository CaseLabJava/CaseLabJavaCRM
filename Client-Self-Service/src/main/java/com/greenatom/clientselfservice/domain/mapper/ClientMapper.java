package com.greenatom.clientselfservice.domain.mapper;

import com.greenatom.clientselfservice.domain.dto.client.ClientRequestDTO;
import com.greenatom.clientselfservice.domain.dto.client.ClientResponseDTO;
import com.greenatom.clientselfservice.domain.dto.security.ClientRegistrationDTO;
import com.greenatom.clientselfservice.domain.entity.Client;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientResponseDTO, Client>{
    ClientResponseDTO toDto(Client s);

    Client toEntity(ClientResponseDTO s);

    Client toEntity(ClientRegistrationDTO s);

    ClientResponseDTO toResponse(ClientRequestDTO s);

    List<ClientResponseDTO> toDto(Page<Client> all);
}
