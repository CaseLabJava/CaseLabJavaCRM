package com.greenatom.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record OrderErrorMessage(

        @Schema(description = "Код ошибки", example = "NO_SUCH_ORDER")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No order with such id")
        String message

) {
}