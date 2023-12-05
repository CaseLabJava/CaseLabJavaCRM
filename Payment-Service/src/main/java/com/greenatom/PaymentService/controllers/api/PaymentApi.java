package com.greenatom.PaymentService.controllers.api;

import com.greenatom.PaymentService.dto.payment.PaymentDto;
import com.greenatom.PaymentService.entities.Payment;
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
                    description = "Информация об оплате успешно принята",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Payment.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возвращает параметры оплаты заказа")
    ResponseEntity<Payment> pay(@RequestBody PaymentDto paymentDto);
}
