package com.greenatom.service;

import com.greenatom.domain.dto.SuccessfulWorkDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SuccessfulWorkService {

    @Transactional(readOnly = true)
    List<SuccessfulWorkDTO> findAll();

    @Transactional(readOnly = true)
    Optional<SuccessfulWorkDTO> findOne(Long id);

    @Transactional
    SuccessfulWorkDTO save(SuccessfulWorkDTO successfulWork);

    @Transactional
    SuccessfulWorkDTO updateSuccessfulWork(SuccessfulWorkDTO successfulWork);

    @Transactional
    void deleteSuccessfulWork(Long id);
}
