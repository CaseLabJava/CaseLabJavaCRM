package com.greenatom.service.impl;

import com.greenatom.domain.dto.EstimateDTO;
import com.greenatom.domain.entity.Estimate;
import com.greenatom.domain.mapper.EstimateMapper;
import com.greenatom.repository.EstimateRepository;
import com.greenatom.service.EstimateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {
    private final Logger log = LoggerFactory.getLogger(EstimateService.class);
    private final EstimateRepository estimateRepository;
    private final EstimateMapper estimateMapper;

    @Override
    public List<EstimateDTO> findAll() {
        log.debug("Request to get all Estimates");
        return estimateMapper.toDto(estimateRepository.findAll());
    }

    @Override
    public Optional<EstimateDTO> findOne(Long id) {
        log.debug("Request to get Estimate : {}", id);
        return Optional.ofNullable(estimateMapper.toDto(estimateRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Request not found with id: " + id))));
    }

    @Override
    public EstimateDTO save(EstimateDTO estimateDTO) {
        log.debug("Request to save estimate : {}", estimateDTO);
        Estimate estimate = estimateMapper.toEntity(estimateDTO);
        estimateRepository.save(estimate);
        return estimateMapper.toDto(estimate);
    }

    @Override
    public EstimateDTO updateEstimate(EstimateDTO estimate) {
        log.debug("Request to partially update Estimate : {}", estimate);
        return estimateRepository
                .findById(estimate.getId())
                .map(existingEvent -> {
                    estimateMapper.partialUpdate(existingEvent, estimate);

                    return existingEvent;
                })
                .map(estimateRepository::save)
                .map(estimateMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteEstimate(Long id) {
        estimateRepository
                .findById(id)
                .ifPresent(estimate -> {
                    estimateRepository.delete(estimate);
                    log.debug("Deleted Estimate: {}", estimate);
                });
    }
}
