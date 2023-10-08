package com.greenatom.service.impl;

import com.greenatom.domain.dto.SupplyDTO;
import com.greenatom.domain.entity.Supply;
import com.greenatom.domain.mapper.SupplyMapper;
import com.greenatom.repository.SupplyRepository;
import com.greenatom.service.SupplyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplyServiceImpl implements SupplyService {
    private final Logger log = LoggerFactory.getLogger(SupplyService.class);
    private final SupplyRepository supplyRepository;
    private final SupplyMapper supplyMapper;

    @Override
    public List<SupplyDTO> findAll() {
        log.debug("Request to get all Supplys");
        return supplyMapper.toDto(supplyRepository.findAll());
    }

    @Override
    public Optional<SupplyDTO> findOne(Long id) {
        log.debug("Request to get Supply : {}", id);
        return Optional.ofNullable(supplyMapper.toDto(supplyRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Request not found with id: " + id))));
    }

    @Override
    public SupplyDTO save(SupplyDTO supplyDTO) {
        log.debug("Request to save supply : {}", supplyDTO);
        Supply supply = supplyMapper.toEntity(supplyDTO);
        supplyRepository.save(supply);
        return supplyMapper.toDto(supply);
    }

    @Override
    public SupplyDTO updateSupply(SupplyDTO supply) {
        log.debug("Request to partially update Supply : {}", supply);
        return supplyRepository
                .findById(supply.getId())
                .map(existingEvent -> {
                    supplyMapper.partialUpdate(existingEvent, supply);

                    return existingEvent;
                })
                .map(supplyRepository::save)
                .map(supplyMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteSupply(Long id) {
        supplyRepository
                .findById(id)
                .ifPresent(supply -> {
                    supplyRepository.delete(supply);
                    log.debug("Deleted Supply: {}", supply);
                });
    }}