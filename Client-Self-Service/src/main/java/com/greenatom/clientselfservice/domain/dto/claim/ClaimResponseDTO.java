package com.greenatom.clientselfservice.domain.dto.claim;

import com.greenatom.clientselfservice.domain.enums.ClaimStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponseDTO {

    @Schema(description = "Id претензии", example = "1")
    private Long id;

    @Schema(description = "Id заказа", example = "1")
    private Long orderId;

    @Schema(description = "Id сотрудника", example = "3")
    private Long employeeId;

    @Schema(description = "Id клиента", example = "2")
    private Long clientId;

    @Schema(description = "Статус претензии", example = "CREATED")
    private ClaimStatus claimStatus;

    @Schema(description = "Время создания претензии")
    private Instant creationTime;

    @Schema(description = "Время разрешения претензии")
    private Instant resolvedTime;
}
