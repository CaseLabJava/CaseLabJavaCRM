package com.greenatom.clientselfservice.utils.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AuthErrorMessage(
        @Schema(description = "Код ошибки", example = "NO_SUCH_<ITEM>")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No such employee")
        String message
) {
}
