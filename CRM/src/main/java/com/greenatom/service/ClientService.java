package com.greenatom.service;

import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;

import java.util.List;

public interface ClientService {

    List<ClientResponseDTO> findAll();

    ClientResponseDTO findOne(Long id);

    ClientResponseDTO save(ClientRequestDTO client);

    ClientResponseDTO updateClient(Long id, ClientRequestDTO client);

    void deleteClient(Long id);

    List<ClientResponseDTO> findClientPageByParams(Integer pageNumber, Integer pageSize, String company, String firstName, String secondName, String patronymic);
}
