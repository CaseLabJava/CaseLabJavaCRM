package com.greenatom.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record DeliveryErrorMessage(
        @Schema(description = "Код ошибки", example = "NO_SUCH_DELIVERY")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No delivery with such id")
        String message) {

}
