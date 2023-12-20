package com.greenatom.clientservice.utils.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorMessage(
        @Schema(description = "Код ошибки", example = "BAD_REQUEST")
        String code,
        @Schema(description = "Сообщение ошибки", example = "Ошибка запроса")
        String message
) {
}
