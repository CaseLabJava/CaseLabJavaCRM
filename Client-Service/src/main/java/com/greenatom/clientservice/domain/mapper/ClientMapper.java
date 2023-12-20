package com.greenatom.clientservice.domain.mapper;

import com.greenatom.clientservice.domain.dto.client.ClientCreateDTO;
import com.greenatom.clientservice.domain.dto.client.ClientRequestDTO;
import com.greenatom.clientservice.domain.dto.client.ClientResponseDTO;
import com.greenatom.clientservice.domain.entity.Client;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientResponseDTO, Client>{
    ClientResponseDTO toDto(Client s);

    Client toEntity(ClientResponseDTO s);

    Client toEntity(ClientCreateDTO s);

    ClientResponseDTO toResponse(ClientRequestDTO s);

    List<ClientResponseDTO> toDto(Page<Client> all);
}
