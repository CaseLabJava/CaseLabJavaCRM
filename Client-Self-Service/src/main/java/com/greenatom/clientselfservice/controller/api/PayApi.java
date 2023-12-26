package com.greenatom.clientselfservice.controller.api;

import com.greenatom.clientselfservice.utils.exception.message.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pay API", description = "API для отправки оплаты в Payment сервис")
public interface PayApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная отправка запроса на kafka",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ошибка отправки запроса на kafka",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение отзыва по id"
    )
    ResponseEntity<Void> pay(
            @Parameter(description = "Id оплаты", example = "1")
            Long paymentId
    );
}
