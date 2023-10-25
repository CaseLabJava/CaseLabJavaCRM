package com.greenatom.controller.api;

import com.greenatom.domain.dto.order.GenerateOrderRequest;
import com.greenatom.domain.dto.order.OrderDTO;
import com.greenatom.domain.dto.order.OrderRequest;
import com.greenatom.utils.exception.message.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Order API - это интерфейс, который описывает набор методов для работы с заявками. Он включает методы
 * для сохранения новых заявок, получения уже существующих заявок и их обновление.
 * @autor Даниил Змаев
 * @version 1.0
 */
//@AccessDeniedResponse
//@SecurityRequirement(name = "bearer-key")
@Tag(name = "Order API", description = "API для работы с заказами")
public interface OrderApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное сохранение заказа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт, клиент или сотрудник по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class)
                            )
                    }
            )
    })
    @Operation(summary = "Создает Order и возвращает OrderDTO")
    ResponseEntity<OrderDTO> addDraftOrder(
            @Parameter(description = "Order Request")
            OrderRequest orderRequest
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная генерация документа"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заказ по переданному id не был найден"
            ),
            @ApiResponse(
                    responseCode = "501",
                    description = "Ошибка генерации"
            )
    })
    @Operation(
            summary = "Генерация докумена заказа"
    )
    ResponseEntity<Void> generateOrder(
            @Parameter(description = "GenerationOrderRequest")
            GenerateOrderRequest orderRequest
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат заказа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заказ по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение заказа по id"
    )

    ResponseEntity<OrderDTO> getOrder(
            @Parameter(description = "Id заказа", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный удаление заказа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заказ по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Заказ по переданному id является подписаным",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление заказа по id, имеющего стататус EMPTY"
    )
    void deleteOrder(
            @Parameter(description = "Id заказа", example = "1")
            Long id
    );

}
