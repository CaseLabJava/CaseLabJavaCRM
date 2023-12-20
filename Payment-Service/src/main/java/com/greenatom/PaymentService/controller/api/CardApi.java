package com.greenatom.paymentservice.controller.api;

import com.greenatom.paymentservice.domain.dto.CardRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Card API", description = "API для работы с картами клиентов")
public interface CardApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Карта успешно добавлена"
            )
    })
    @Operation(summary = "Добавление новой карты")
    ResponseEntity<Void> createCard(CardRequestDto cardRequestDto);
}