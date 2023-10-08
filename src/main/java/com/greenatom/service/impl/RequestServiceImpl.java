package com.greenatom.service.impl;

import com.greenatom.domain.dto.RequestDTO;
import com.greenatom.domain.entity.Request;
import com.greenatom.domain.mapper.RequestMapper;
import com.greenatom.repository.RequestRepository;
import com.greenatom.service.RequestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    public List<RequestDTO> findAll() {
        log.debug("Request to get all Requests");
        return requestMapper.toDto(requestRepository.findAll());
    }

    @Override
    public Optional<RequestDTO> findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return Optional.ofNullable(requestMapper.toDto(requestRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Request not found with id: " + id))));
    }

    @Override
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Request to save request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public RequestDTO updateRequest(RequestDTO request) {
        log.debug("Request to partially update Request : {}", request);
        return requestRepository
                .findById(request.getId())
                .map(existingEvent -> {
                    requestMapper.partialUpdate(existingEvent, request);

                    return existingEvent;
                })
                .map(requestRepository::save)
                .map(requestMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository
                .findById(id)
                .ifPresent(request -> {
                    requestRepository.delete(request);
                    log.debug("Deleted Request: {}", request);
                });
    }
}
