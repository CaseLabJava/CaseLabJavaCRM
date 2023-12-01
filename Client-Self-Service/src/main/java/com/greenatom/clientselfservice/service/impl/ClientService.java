package com.greenatom.clientselfservice.service.impl;

import com.greenatom.clientselfservice.domain.dto.client.ClientResponseDTO;
import com.greenatom.clientselfservice.domain.dto.security.ClientRegistrationDTO;
import com.greenatom.clientselfservice.domain.entity.Client;
import com.greenatom.clientselfservice.domain.mapper.ClientMapper;
import com.greenatom.clientselfservice.repository.ClientRepository;
import com.greenatom.clientselfservice.utils.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public Optional<Client> findByEmail(String email){
        return clientRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findById(Long id){
        return clientMapper.toDto(clientRepository.findById(id).orElseThrow(ClientException.CODE.NO_SUCH_CLIENT::get));
    }

    public ClientResponseDTO save(ClientRegistrationDTO registrationDTO){
        Client client = clientMapper.toEntity(registrationDTO);
        client.setPassword(encoder.encode(registrationDTO.getPassword()));
        clientRepository.save(client);
        return clientMapper.toDto(client);
    }
}
