package com.greenatom.controller.api;

import com.greenatom.domain.dto.item.OrderItemDTO;
import com.greenatom.utils.exception.message.OrderItemErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "OrderItem API", description = "API для работы с продуктами в заказе")
public interface OrderItemApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат продукта в заказе",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderItemDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт в заказе по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            OrderItemErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение продукта в заказе по id"
    )
    ResponseEntity<OrderItemDTO> getOrderItem(
            @Parameter(description = "Id продукта в заказе", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление продукта в заказе",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderItemDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт в заказе по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            OrderItemErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление продукта в заказе по id"
    )
    ResponseEntity<Void> deleteOrderItem(
            @Parameter
            Long id
    );
}
