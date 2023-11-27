package com.greenatom.domain.dto.preparing_order;

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
public class PreparingOrderSearchCriteria {

    @Schema(description = "Id заказа", example = "1")
    private Long orderId;

    @Schema(description = "Id сотрудника", example = "3")
    private Long employeeId;

    @Schema(description = "Статус сборки заказа", example = "IN_PROCESS")
    private String preparingOrderStatus;

    @Schema(description = "Время начала сборки заказа", example = "2016-01-06T15:22" +
            ":53" +
            ".403Z")
    private Instant startTime;

    @Schema(description = "Время окончания сборки заказа", example = "2016-01-06T15:23" +
            ":53" +
            ".403Z")
    private Instant endTime;
}
