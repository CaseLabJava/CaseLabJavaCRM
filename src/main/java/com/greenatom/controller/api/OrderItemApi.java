package com.greenatom.controller.api;

import com.greenatom.domain.dto.order.OrderDTO;
import com.greenatom.domain.dto.item.OrderItemDTO;
import com.greenatom.domain.dto.item.OrderItemRequest;
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
                    description = "Успешное сохранение заявки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "501",
                    description = "Ошибка сохранения заявки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возращает OrderDTO")
    ResponseEntity<Void> addOrderItem(
            @Parameter(description = "OrderDTO")
            OrderItemRequest orderItemRequest
    );
}
