package com.greenatom.domain.dto.courier;

import com.greenatom.domain.enums.ClaimStatus;
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
public class CourierResponseDTO {

    @Schema(description = "Id курьера", example = "1")
    private Long id;

    @Schema(description = "Id заказа", example = "1")
    private Long orderId;

    @Schema(description = "Id сотрудника", example = "3")
    private Long employeeId;

    @Schema(description = "Статус претензии", example = "CREATED")
    private ClaimStatus claimStatus;

    @Schema(description = "Время создания претензии", example = "2016-01-06T15:22:53.403Z")
    private Instant creationTime;

    @Schema(description = "Время разрешения претензии", example = "2016-01-06T15:23:53" +
            ".403Z")
    private Instant resolvedTime;
}
