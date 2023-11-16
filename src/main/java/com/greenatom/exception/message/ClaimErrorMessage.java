package com.greenatom.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ClaimErrorMessage(
        @Schema(description = "Код ошибки", example = "NO_SUCH_CLAIM")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No claim with such id")
        String message
) {
}