package com.greenatom.service.impl;

import com.greenatom.domain.dto.claim.ClaimRequestDTO;
import com.greenatom.domain.dto.claim.ClaimResponseDTO;
import com.greenatom.domain.entity.Claim;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.enums.ClaimStatus;
import com.greenatom.domain.mapper.ClaimMapper;
import com.greenatom.domain.mapper.ClientMapper;
import com.greenatom.repository.ClaimRepository;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.service.ClaimService;
import com.greenatom.utils.exception.ClaimException;
import com.greenatom.utils.exception.EmployeeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClaimServiceImpl implements ClaimService {
    private final EmployeeRepository employeeRepository;
    private final ClientMapper clientMapper;
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
    public ClaimResponseDTO save(ClaimRequestDTO claimRequestDTO) {
        Claim claim = claimMapper.toEntity(claimRequestDTO);
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
    public ClaimResponseDTO resolveClaim(ClaimRequestDTO claimDTO, ClaimStatus status) {

        if(claimDTO.getClaimStatus().equals(ClaimStatus.IN_WORK)
                &&(status.equals(ClaimStatus.RESOLVED_FOR_CLIENT)
                ||status.equals(ClaimStatus.RESOLVED_FOR_COMPANY))){
            Claim claim =claimMapper.toEntity(claimDTO);
            claim.setClaimStatus(status);
            claimRepository.save(claim);
            return claimMapper.toDto(claim);
        } else{
            throw ClaimException.CODE.INVALID_STATUS.get();
        }
    }

    @Override
    @Transactional
    public ClaimResponseDTO appointClaim(ClaimRequestDTO claimDTO, Long id) {
        Claim claim = claimMapper.toEntity(claimDTO);
        Employee employee = employeeRepository.findById(id).orElseThrow(
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
                })
                .map(claimRepository::save)
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
