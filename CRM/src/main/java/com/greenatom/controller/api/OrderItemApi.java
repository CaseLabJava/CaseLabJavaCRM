package com.greenatom.controller.api;

import com.greenatom.domain.dto.orderitem.OrderItemResponseDTO;
import com.greenatom.exception.message.ErrorMessage;
import com.greenatom.exception.message.OrderItemErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
                                    schema = @Schema(implementation = OrderItemResponseDTO.class)
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
    ResponseEntity<OrderItemResponseDTO> getOrderItem(
            @Parameter(description = "Id продукта в заказе", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат продуктов в заказе",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderItemResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный статус",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение всех продуктов в заказе"
    )
    ResponseEntity<Page<OrderItemResponseDTO>> getAllOrderItems(
            @Parameter(description = "Начальная страница") Integer pagePosition,
            @Parameter(description = "Размер страницы") Integer pageLength,
            @Parameter(description = "Id заказа") Long orderId,
            @Parameter(description = "Id продукта") Long productId,
            @Parameter(description = "Имя продукта") String productName,
            @Parameter(description = "Единица измерения продукта") String unit,
            @Parameter(description = "Количество на складе") Long storageAmount,
            @Parameter(description = "Стоимость продукта") Long cost,
            @Parameter(description = "Поле для сортировки") String sortBy,
            @Parameter(
                    in = ParameterIn.QUERY,
                    name = "sortDirection",
                    schema = @Schema(allowableValues = {
                            "ASC",
                            "DESC"
                    }))
            Sort.Direction sortDirection);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное удаление продукта в заказе",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderItemResponseDTO.class)
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
