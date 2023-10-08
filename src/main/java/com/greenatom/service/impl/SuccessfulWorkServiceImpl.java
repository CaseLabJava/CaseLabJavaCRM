package com.greenatom.service.impl;

import com.greenatom.domain.dto.SuccessfulWorkDTO;
import com.greenatom.domain.entity.SuccessfulWork;
import com.greenatom.domain.mapper.SuccessfulWorkMapper;
import com.greenatom.repository.SuccessfulWorkRepository;
import com.greenatom.service.SuccessfulWorkService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuccessfulWorkServiceImpl implements SuccessfulWorkService {

    private final Logger log = LoggerFactory.getLogger(SuccessfulWorkService.class);
    private final SuccessfulWorkRepository successfulWorkRepository;
    private final SuccessfulWorkMapper successfulWorkMapper;

    @Override
    public List<SuccessfulWorkDTO> findAll() {
        log.debug("Request to get all SuccessfulWorks");
        return successfulWorkMapper.toDto(successfulWorkRepository.findAll());
    }

    @Override
    public Optional<SuccessfulWorkDTO> findOne(Long id) {
        log.debug("Request to get SuccessfulWork : {}", id);
        return Optional.ofNullable(successfulWorkMapper.toDto(successfulWorkRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Request not found with id: " + id))));
    }

    @Override
    public SuccessfulWorkDTO save(SuccessfulWorkDTO successfulWorkDTO) {
        log.debug("Request to save successfulWork : {}", successfulWorkDTO);
        SuccessfulWork successfulWork = successfulWorkMapper.toEntity(successfulWorkDTO);
        successfulWorkRepository.save(successfulWork);
        return successfulWorkMapper.toDto(successfulWork);
    }

    @Override
    public SuccessfulWorkDTO updateSuccessfulWork(SuccessfulWorkDTO successfulWork) {
        log.debug("Request to partially update SuccessfulWork : {}",
                successfulWork);
        return successfulWorkRepository
                .findById(successfulWork.getId())
                .map(existingEvent -> {
                    successfulWorkMapper.partialUpdate(existingEvent, successfulWork);

                    return existingEvent;
                })
                .map(successfulWorkRepository::save)
                .map(successfulWorkMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteSuccessfulWork(Long id) {
        successfulWorkRepository
                .findById(id)
                .ifPresent(successfulWork -> {
                    successfulWorkRepository.delete(successfulWork);
                    log.debug("Deleted SuccessfulWork: {}", successfulWork);
                });
    }
}

