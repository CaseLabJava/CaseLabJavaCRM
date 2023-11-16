package com.greenatom.service.impl;

import com.greenatom.domain.dto.claim.ClaimCreationDTO;
import com.greenatom.domain.dto.claim.ClaimRequestDTO;
import com.greenatom.domain.dto.claim.ClaimResponseDTO;
import com.greenatom.domain.entity.Claim;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.enums.ClaimStatus;
import com.greenatom.domain.mapper.ClaimMapper;
import com.greenatom.exception.ClaimException;
import com.greenatom.exception.EmployeeException;
import com.greenatom.repository.ClaimRepository;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.service.ClaimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClaimServiceImpl implements ClaimService {
    private final EmployeeRepository employeeRepository;
    private final ClaimRepository claimRepository;
    private final ClaimMapper claimMapper;

    @Override
    @Transactional(readOnly = true)
    public ClaimResponseDTO findOne(Long id) {
        return claimMapper.toDto(claimRepository
                .findById(id)
                .orElseThrow(ClaimException.CODE.NO_SUCH_CLAIM::get));
    }

    @Override
    @Transactional
    public ClaimResponseDTO save(ClaimCreationDTO claimRequestDTO) {
        Claim claim = claimMapper.toClaim(claimRequestDTO);
        claim.setCreationTime(Instant.now());
        claim.setClaimStatus(ClaimStatus.CREATED);
        claimRepository.save(claim);
        return claimMapper.toDto(claim);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaimResponseDTO> findUnassignedClaims(Integer pageNumber, Integer pageSize) {
        return claimRepository.findByEmployeeId(null, PageRequest.of(pageNumber,
                pageSize)).map(claimMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ClaimResponseDTO resolveClaim(Long claimId, ClaimStatus status) {
        Claim claim = claimRepository.findById(claimId).orElseThrow(
                ClaimException.CODE.NO_SUCH_CLAIM::get);
        if(claim.getClaimStatus().equals(ClaimStatus.IN_WORK)
                &&(status.equals(ClaimStatus.RESOLVED_FOR_CLIENT)
                ||status.equals(ClaimStatus.RESOLVED_FOR_COMPANY))){
            claim.setClaimStatus(status);
            claimRepository.save(claim);
            return claimMapper.toDto(claim);
        } else{
            throw ClaimException.CODE.INVALID_STATUS.get();
        }
    }

    @Override
    @Transactional
    public ClaimResponseDTO appointClaim(Long claimId, Long employeeId) {
        Claim claim = claimRepository.findById(claimId).orElseThrow(
                ClaimException.CODE.NO_SUCH_CLAIM::get);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                EmployeeException.CODE.NO_SUCH_EMPLOYEE::get);
        claim.setEmployee(employee);
        claimRepository.save(claim);
        return claimMapper.toDto(claim);
    }

    @Override
    @Transactional
    public ClaimResponseDTO updateClaim(Long id, ClaimRequestDTO claimRequestDTO) {
        return claimRepository
                .findById(id)
                .map(existingEvent -> {
                    claimMapper.partialUpdate(existingEvent,
                            claimMapper.toResponse(claimRequestDTO));
                    return existingEvent;
                }).map(claimRepository::save)
                .map(claimMapper::toDto).orElseThrow(
                        ClaimException.CODE.NO_SUCH_CLAIM::get);
    }

    @Override
    @Transactional
    public void deleteClaim(Long id) {
        claimRepository
                .findById(id)
                .ifPresent(claimRepository::delete);
    }
}
