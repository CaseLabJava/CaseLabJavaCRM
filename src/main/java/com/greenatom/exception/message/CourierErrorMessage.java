package com.greenatom.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CourierErrorMessage (
        @Schema(description = "Код ошибки", example = "NO_SUCH_COURIER")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No courier with such id")
        String message){
}
