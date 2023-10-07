package com.greenatom.service;

import com.greenatom.domain.dto.RequestDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    @Transactional(readOnly = true)
    List<RequestDTO> findAll();

    @Transactional(readOnly = true)
    Optional<RequestDTO> findOne(Long id);

    @Transactional
    RequestDTO save(RequestDTO request);

    @Transactional
    RequestDTO updateRequest(RequestDTO request);

    @Transactional
    void deleteRequest(Long id);
}
