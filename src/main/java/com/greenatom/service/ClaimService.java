package com.greenatom.service;

import com.greenatom.domain.dto.claim.ClaimCreationDTO;
import com.greenatom.domain.dto.claim.ClaimRequestDTO;
import com.greenatom.domain.dto.claim.ClaimResponseDTO;
import com.greenatom.domain.enums.ClaimStatus;

import java.util.List;

public interface ClaimService {
    ClaimResponseDTO findOne(Long id);

    void deleteClaim(Long id);

    ClaimResponseDTO updateClaim(Long id, ClaimRequestDTO claim);

    ClaimResponseDTO save(ClaimCreationDTO claim);

    List<ClaimResponseDTO> findUnassignedClaims(Integer pageNumber, Integer pageSize);

    ClaimResponseDTO resolveClaim(Long claim, ClaimStatus status);

    ClaimResponseDTO appointClaim(Long claim, Long id);
}
