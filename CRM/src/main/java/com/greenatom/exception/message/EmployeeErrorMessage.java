package com.greenatom.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record EmployeeErrorMessage(
        @Schema(description = "Код ошибки", example = "NO_SUCH_EMPLOYEE")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No employee with such id")
        String message) {

}
