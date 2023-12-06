package com.greenatom.paymentservice.controller.api;

import com.greenatom.paymentservice.domain.dto.PaymentRequestDto;
import com.greenatom.paymentservice.domain.entity.Card;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Payment API", description = "API для сервиса оплаты")
public interface PaymentApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Карта успешно принята к оплате",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Card.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возвращает параметры карты")
    ResponseEntity<Card> pay(@RequestBody PaymentRequestDto paymentDto);
}
