package com.greenatom.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ErrorMessage(
        @Schema(description = "Код ошибки", example = "BAD_REQUEST")
        String code,
        @Schema(description = "Сообщение ошибки", example = "Ошибка запроса")
        String message
) {
}
