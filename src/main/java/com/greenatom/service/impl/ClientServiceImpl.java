package com.greenatom.service.impl;

import com.greenatom.domain.dto.ClientDTO;
import com.greenatom.domain.entity.Client;
import com.greenatom.domain.mapper.ClientMapper;
import com.greenatom.repository.ClientRepository;
import com.greenatom.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientDTO> findAll() {
        return clientMapper.toDto(clientRepository.findAll());
    }

    @Override
    public ClientDTO findOne(Long id) {
        return clientMapper.toDto(clientRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Order not found with id: " + id)));
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDTO updateClient(ClientDTO client) {
        return clientRepository
                .findById(client.getId())
                .map(existingEvent -> {
                    clientMapper.partialUpdate(existingEvent, client);

                    return existingEvent;
                })
                .map(clientRepository::save)
                .map(clientMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository
                .findById(id)
                .ifPresent(clientRepository::delete);
    }

    @Override
    public List<ClientDTO> findClientPageByParams(Integer pageNumber, Integer pageSize, String company, String firstName, String secondName, String patronymic) {
        return clientRepository.findClientsByCompanyContainingAndNameContainingAndSurnameContainingAndPatronymicContaining(PageRequest.of(pageNumber, pageSize),
                        company,
                        firstName,
                        secondName,
                        patronymic)
                .map(clientMapper::toDto)
                .toList();
    }
}
