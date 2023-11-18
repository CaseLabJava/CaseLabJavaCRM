package com.greenatom.domain.dto.delivery;

import com.greenatom.domain.enums.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Описание модели запроса \"Доставка\"")
public class DeliverySearchCriteria {

    @Schema(description = "Id доставки", example = "1")
    private Long id;

    @Schema(description = "Id заказа", example = "1")
    private Long orderId;

    @Schema(description = "Id курьера", example = "3")
    private Long courierId;

    @Schema(description = "Статус доставки", example = "IN_PROCESS")
    private String deliveryStatus;

    @Schema(description = "Время начала доставки", example = "2016-01-06T15:22" +
            ":53" +
            ".403Z")
    private Instant startTime;

    @Schema(description = "Время окончания доставки", example = "2016-01-06T15:23:53" +
            ".403Z")
    private Instant endTime;

}
