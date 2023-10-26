package com.greenatom.config.swagger.annotation;

import com.greenatom.utils.exception.message.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ApiResponse(
        responseCode = "403",
        description = "Недостаточно прав доступа для выполнения",
        content = {
                @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorMessage.class)
                )}
)
public @interface AccessDeniedResponse {
}
