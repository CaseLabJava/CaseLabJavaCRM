package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.claim.ClaimCreationDTO;
import com.greenatom.domain.dto.claim.ClaimRequestDTO;
import com.greenatom.domain.dto.claim.ClaimResponseDTO;
import com.greenatom.domain.entity.Claim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Claim} and its DTO called {@link ClaimResponseDTO}.
 */

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, ClientMapper.class})
public interface ClaimMapper extends EntityMapper<ClaimResponseDTO, Claim> {

    @Mapping(target = "employeeId", source = "s.employee.id")
    @Mapping(target = "clientId", source = "s.client.id")
    @Mapping(target = "orderId", source = "s.order.id")
    ClaimResponseDTO toDto(Claim s);

    Claim toEntity(ClaimResponseDTO s);

    Claim toEntity(ClaimRequestDTO s);

    ClaimResponseDTO toResponse(ClaimRequestDTO s);

    Claim toClaim(ClaimCreationDTO s);
}
