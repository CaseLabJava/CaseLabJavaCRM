package com.greenatom.controller.api;

import com.greenatom.domain.dto.order.GenerateOrderRequestDTO;
import com.greenatom.domain.dto.order.OrderRequestDTO;
import com.greenatom.domain.dto.order.OrderResponseDTO;
import com.greenatom.utils.exception.message.OrderErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Order API - это интерфейс, который описывает набор методов для работы с заявками. Он включает методы
 * для сохранения новых заявок, получения уже существующих заявок и их обновление.
 * @author Даниил Змаев
 * @version 1.0
 */
//@AccessDeniedResponse
//@SecurityRequirement(name = "bearer-key")
@Tag(name = "Order API", description = "API для работы с заказами")
public interface OrderApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат заказов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заказы не были найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",

                                    schema = @Schema(implementation = OrderErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение заказа"
    )
    ResponseEntity<List<OrderResponseDTO>> getOrders(
            @Parameter(description = "Номер страницы", example = "0")
            Integer limit,
            @Parameter(description = "Количество элементов", example = "10")
            Integer offset,
            @Parameter(description = "Поле сортировки", example = "orderDate")
            String sortField,
            @Parameter(description = "Порядок сортировки", example = "asc")
            String sortOrder,
            @Parameter(description = "Статус заказа")
            String orderStatus,
            @Parameter(description = "Ссылка на папку")
            String linkToFolder
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное сохранение заказа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт, клиент или сотрудник по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class)
                            )
                    }
            )
    })
    @Operation(summary = "Создает Order и возвращает OrderDTO")
    OrderResponseDTO addDraftOrder(
            @Parameter(description = "Order Request")
            OrderRequestDTO orderRequestDTO
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
    void generateOrder(
            @Parameter(description = "GenerationOrderRequest")
            GenerateOrderRequestDTO orderRequest
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат заказа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заказ по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            OrderErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение заказа по id"
    )
    OrderResponseDTO getOrder(
            @Parameter(description = "Id заказа", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное завершение заказа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заказ по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            OrderErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Завершение заказа"
    )
    OrderResponseDTO finishOrder(
            @Parameter(description = "Id заказа", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат заказов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение заказов по id работника"
    )
    List<OrderResponseDTO> getAllOrders(
            @Parameter(description = "Позиция страницы", example = "0")
            Integer pagePosition,
            @Parameter(description = "Длина страницы", example = "5")
            Integer pageLength,
            @Parameter(description = "Id работника", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный удаление заказа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заказ по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            OrderErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Заказ по переданному id является подписанным",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            OrderErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление заказа по id, имеющего статус EMPTY"
    )
    void deleteOrder(
            @Parameter(description = "Id заказа", example = "1")
            Long id
    );

}
