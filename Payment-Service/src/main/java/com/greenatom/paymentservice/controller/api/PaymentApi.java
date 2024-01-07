package com.greenatom.PaymentService.controller.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;


@Tag(name = "Payment API", description = "API для сервиса оплаты")
public interface PaymentApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Карта успешно принята к оплате"
            )
    })
    @Operation(summary = "Возвращает параметры карты")
    ResponseEntity<Void> createPayment(
            @Parameter(description = "Id клиента") Long userId,
            @Parameter(description = "Id заказа") Long orderId,
            @Parameter(description = "Сумма платежа") Long sumOfPay);
}
