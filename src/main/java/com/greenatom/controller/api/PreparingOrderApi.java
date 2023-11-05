package com.greenatom.controller.api;

import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "PreparingOrder API", description = "API для работы со сборкой заказа")
public interface PreparingOrderApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат заказов в обработке",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PreparingOrderResponseDTO.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение всех заказов по статусу"
    )
    ResponseEntity<List<PreparingOrderResponseDTO>> getPreparingOrders(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                                       @RequestParam(defaultValue = "WAITING_FOR_PREPARING", required = false) String status);
}
