package com.greenatom.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReviewErrorMessage(
        @Schema(description = "Код ошибки", example = "NO_SUCH_REVIEW")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No review with such id")
        String message
) {
}