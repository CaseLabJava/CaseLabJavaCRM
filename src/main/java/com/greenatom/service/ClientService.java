package com.greenatom.service;

import com.greenatom.domain.dto.ClientDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    @Transactional(readOnly = true)
    List<ClientDTO> findAll();

    @Transactional(readOnly = true)
    Optional<ClientDTO> findOne(Long id);

    @Transactional
    ClientDTO save(ClientDTO client);

    @Transactional
    ClientDTO updateClient(ClientDTO client);

    @Transactional
    void deleteClient(Long id);
}
