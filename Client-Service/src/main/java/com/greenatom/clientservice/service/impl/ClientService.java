package com.greenatom.clientservice.service.impl;

import com.greenatom.clientservice.domain.dto.client.*;
import com.greenatom.clientservice.domain.entity.Client;
import com.greenatom.clientservice.domain.mapper.ClientMapper;
import com.greenatom.clientservice.repository.ClientRepository;
import com.greenatom.clientservice.repository.criteria.ClientCriteriaRepository;
import com.greenatom.clientservice.utils.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final PasswordEncoder passwordEncoder;

    private final ClientCriteriaRepository clientCriteriaRepository;

    public ClientResponseDTO findOneById(Long id){
        return clientMapper.toDto(clientRepository.findById(id)
                .orElseThrow(ClientException.CODE.NO_SUCH_CLIENT::get));
    }

    public Page<ClientResponseDTO> findAll(EntityPage entityPage, ClientSearchCriteria clientSearchCriteria) {
        return clientCriteriaRepository.findAllWithFilters(entityPage, clientSearchCriteria).map(clientMapper::toDto);
    }

    @Transactional
    public ClientResponseDTO save(ClientCreateDTO clientCreateDTO){
        Client client = clientMapper.toEntity(clientCreateDTO);
        client.setPassword(passwordEncoder.encode(clientCreateDTO.getPassword()));
        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    public Optional<Client> findOneByEmail(String email){
        return clientRepository.findByEmail(email);
    }

    @Transactional
    public void deleteClient(Long id) {
        clientRepository
                .findById(id)
                .ifPresent(clientRepository::delete);
    }

    @Transactional
    public ClientResponseDTO updateClient(Long id, ClientRequestDTO clientRequestDTO) {
        return clientRepository
                .findById(id)
                .map(existingEvent -> {
                    clientMapper.partialUpdate(existingEvent, clientMapper.toResponse(clientRequestDTO));
                    return existingEvent;
                })
                .map(clientRepository::save)
                .map(clientMapper::toDto).orElseThrow(
                        ClientException.CODE.NO_SUCH_CLIENT::get);
    }
}
