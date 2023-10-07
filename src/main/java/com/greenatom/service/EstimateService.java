package com.greenatom.service;

import com.greenatom.domain.dto.EstimateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EstimateService {

    @Transactional(readOnly = true)
    List<EstimateDTO> findAll();

    @Transactional(readOnly = true)
    Optional<EstimateDTO> findOne(Long id);

    @Transactional
    EstimateDTO save(EstimateDTO estimate);

    @Transactional
    EstimateDTO updateEstimate(EstimateDTO estimate);

    @Transactional
    void deleteEstimate(Long id);
}
