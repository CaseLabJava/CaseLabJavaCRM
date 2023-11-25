package com.greenatom.controller.api;


import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.domain.dto.order.OrderRequestDTO;
import com.greenatom.domain.dto.order.OrderResponseDTO;
import com.greenatom.exception.message.ErrorMessage;
import com.greenatom.exception.message.OrderErrorMessage;
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

import java.security.Principal;
import java.time.Instant;

/**
 * Order API - это интерфейс, который описывает набор методов для работы с заявками. Он включает методы
 * для сохранения новых заявок, получения уже существующих заявок и их обновление.
 *
 * @author Даниил Змаев
 * @version 1.0
 */

@Tag(name = "Order API", description = "API для работы с заказами")
public interface OrderApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат заказов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeResponseDTO.class)
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
            summary = "Получение всех заказов"
    )
    ResponseEntity<Page<OrderResponseDTO>> getAllOrders(@Parameter(description = "Начальная страница") Integer pagePosition,
                                                        @Parameter(description = "Размер страницы") Integer pageSize,
                                                        @Parameter(description = "Ссылка на директорию с документами") String linkToFolder,
                                                        @Parameter(description = "Дата заказа") Instant orderDate,
                                                        @Parameter(in = ParameterIn.QUERY,
                                                                description = "Статус заказа",
                                                                name = "orderStatus",
                                                                schema = @Schema(allowableValues = {
                                                                        "DRAFT",
                                                                        "SIGNED_BY_EMPLOYEE",
                                                                        "SIGNED_BY_CLIENT",
                                                                        "IN_PROCESS",
                                                                        "FINISHED",
                                                                        "DELIVERY_FINISHED"
                                                                }))
                                                        String orderStatus,
                                                        @Parameter(in = ParameterIn.QUERY,
                                                                name = "deliveryType",
                                                                schema = @Schema(allowableValues = {
                                                                        "DELIVERY",
                                                                        "PICKUP"
                                                                }))
                                                        String deliveryType,
                                                        @Parameter(description = "Id клиента") Long client,
                                                        @Parameter(description = "Id сотрудника") Long employee,
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
                    description = "Продукт, заказ или сотрудник по переданному id не " +
                            "был" +
                            " " +
                            "найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class)
                            )
                    }
            )
    })
    @Operation(summary = "Создает Order и возвращает OrderDTO")
    ResponseEntity<OrderResponseDTO> addDraftOrder(
            Principal principal,
            @Parameter(description = "Order Request")
            OrderRequestDTO orderRequestDTO
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
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
            @Parameter(description = "Id заказа", example = "1")
            Long orderId,
            @Parameter(description = "Логин сотрудника", example = "1")
            Principal principal
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
                                            ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сотрудник по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "405",
                    description = "У сотрудника нет доступа к данному заказу",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение заказа по id"
    )
    ResponseEntity<OrderResponseDTO> getOrder(
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
                                            ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сотрудник по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "405",
                    description = "У сотрудника нет доступа к данному заказу",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Завершение заказа"
    )
    ResponseEntity<OrderResponseDTO> finishOrder(
            @Parameter(description = "Логин сотрудника", example = "1")
            Principal principal,
            @Parameter(description = "Id заказа", example = "1")
            Long orderId
    );


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
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
    ResponseEntity<Void> deleteOrder(
            @Parameter(description = "Id заказа", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление заказа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            OrderResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Указанный заказ не был найден",
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
            summary = "Обновление информации о заказе"
    )
    ResponseEntity<OrderResponseDTO> updateOrder(
            @Parameter(description = "Id заказа")
            Long id,
            @Parameter(description = "Информация о заказе")
            OrderResponseDTO orderResponseDTO
    );
}
