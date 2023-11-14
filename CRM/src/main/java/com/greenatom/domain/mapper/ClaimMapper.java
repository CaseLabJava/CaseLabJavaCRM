package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.claim.ClaimRequestDTO;
import com.greenatom.domain.dto.claim.ClaimResponseDTO;
import com.greenatom.domain.entity.Claim;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Claim} and its DTO called {@link ClaimResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface ClaimMapper extends EntityMapper<ClaimResponseDTO, Claim> {
    ClaimResponseDTO toDto(Claim s);

    Claim toEntity(ClaimResponseDTO s);

    Claim toEntity(ClaimRequestDTO s);

    ClaimResponseDTO toResponse(ClaimRequestDTO s);
}
