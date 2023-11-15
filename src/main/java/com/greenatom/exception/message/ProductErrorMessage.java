package com.greenatom.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductErrorMessage(
        @Schema(description = "Код ошибки", example = "NO_SUCH_PRODUCT")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No product with such id")
        String message) {
}
