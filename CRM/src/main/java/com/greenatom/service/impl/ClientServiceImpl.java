package com.greenatom.service.impl;

import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.domain.dto.client.ClientSearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.entity.Client;
import com.greenatom.domain.mapper.ClientMapper;
import com.greenatom.exception.ClientException;
import com.greenatom.repository.ClientRepository;
import com.greenatom.repository.criteria.ClientCriteriaRepository;
import com.greenatom.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClientServiceImpl является сервисом для работы с клиентами. Он использует репозиторий ClientRepository
 * для доступа к данным, преобразует их в формат DTO с помощью ClientMapper и возвращает список клиентов или
 * конкретный клиент по его ID.
 * @author Максим Быков
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientCriteriaRepository clientCriteriaRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> findAll(EntityPage entityPage, ClientSearchCriteria clientSearchCriteria) {
        return clientCriteriaRepository.findAllWithFilters(entityPage, clientSearchCriteria).map(clientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO findOne(Long id) {
        return clientMapper.toDto(clientRepository
                .findById(id)
                .orElseThrow(ClientException.CODE.NO_SUCH_CLIENT::get));
    }

    @Override
    @Transactional
    public ClientResponseDTO save(ClientRequestDTO clientRequestDTO) {
        Client client = clientMapper.toEntity(clientRequestDTO);
        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
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

    @Override
    @Transactional
    public void deleteClient(Long id) {
        clientRepository
                .findById(id)
                .ifPresent(clientRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponseDTO> findClientPageByParams(Integer pageNumber,
                                                          Integer pageSize,
                                                          String company,
                                                          String firstName,
                                                          String secondName,
                                                          String patronymic) {
        return clientRepository
                .findClientsByCompanyContainingAndNameContainingAndSurnameContainingAndPatronymicContaining(
                        PageRequest.of(pageNumber, pageSize),
                        company,
                        firstName,
                        secondName,
                        patronymic)
                .map(clientMapper::toDto)
                .toList();
    }
}
