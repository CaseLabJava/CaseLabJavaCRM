package com.greenatom.service;

import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.domain.dto.client.ClientSearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {

    Page<ClientResponseDTO> findAll(EntityPage entityPage, ClientSearchCriteria clientSearchCriteria);

    ClientResponseDTO findOne(Long id);

    ClientResponseDTO save(ClientRequestDTO client);

    ClientResponseDTO updateClient(Long id, ClientRequestDTO client);

    void deleteClient(Long id);

    List<ClientResponseDTO> findClientPageByParams(Integer pageNumber, Integer pageSize, String company, String firstName, String secondName, String patronymic);
}
