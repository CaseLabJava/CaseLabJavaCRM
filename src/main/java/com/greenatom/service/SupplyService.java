package com.greenatom.service;

import com.greenatom.domain.dto.SupplyDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SupplyService {

    @Transactional(readOnly = true)
    List<SupplyDTO> findAll();

    @Transactional(readOnly = true)
    Optional<SupplyDTO> findOne(Long id);

    @Transactional
    SupplyDTO save(SupplyDTO supply);

    @Transactional
    SupplyDTO updateSupply(SupplyDTO supply);

    @Transactional
    void deleteSupply(Long id);
}
