package com.greenatom.clientselfservice.utils.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ClientErrorMessage(
        @Schema(description = "Код ошибки", example = "NO_SUCH_CLIENT")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No client with such id")
        String message
) {
}
