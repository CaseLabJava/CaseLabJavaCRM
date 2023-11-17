package com.greenatom.controller.api;

import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.exception.message.EmployeeErrorMessage;
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

import java.time.Instant;

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
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сборки заказа по переданным параметрам не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение всех заказов по статусу"
    )
    ResponseEntity<Page<PreparingOrderResponseDTO>> getPreparingOrders(@Parameter(description = "Начальный номер страницы", example = "0") Integer pageNumber,
                                                                       @Parameter(description = "Размер страницы", example = "10") Integer pageSize,
                                                                       @Parameter(description = "Id заказа", example = "1") Long orderId,
                                                                       @Parameter(description = "Id кладовщика", example = "1") Long employeeId,
                                                                       @Parameter(description = "Статус сборки заказа",
                                                                               in = ParameterIn.QUERY,
                                                                               name = "status",
                                                                               schema = @Schema(allowableValues = {
                                                                                       "WAITING_FOR_PREPARING",
                                                                                       "IN_PROCESS", "DONE"})) String deliveryStatus,
                                                                       @Parameter(description = "Начало сборки заказа",
                                                                               example ="2016-01-06T15:22:53.403Z" ) Instant startTime,
                                                                       @Parameter(description = "Конец сборки",
                                                                               example = "2016-01-06T15:23:53.403Z") Instant endTime,
                                                                       @Parameter(description = "Поле для сортировки") String sortBy,
                                                                       @Parameter(
                                                                               in = ParameterIn.QUERY,
                                                                               description = "Порядок сортировки",
                                                                               name = "sortDirection",
                                                                               schema = @Schema(allowableValues = {
                                                                                       "ASC",
                                                                                       "DESC"
                                                                               })) Sort.Direction sortDirection);
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат сборки заказа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PreparingOrderResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сборки заказа по переданному id не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            )
    })
    ResponseEntity<PreparingOrderResponseDTO> findById(@Parameter(description = "Id сборки заказа") Long id);
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное назначение сборщика"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Нет прав доступа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сотрудника или сборки заказа по переданному id не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Назначение сборщика на заказ"
    )
    ResponseEntity<Void> appointCollector(@Parameter(description = "Id сотрудника") Long employeeId,
                                          @Parameter(description = "Id сборки заказа") Long preparingOrderId);


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное завершение сборки заказа"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Нет прав доступа",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сотрудника или сборки заказа по переданному id не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Назначение сборщика на заказ"
    )
    ResponseEntity<Void> finishPreparingOrder(@Parameter(description = "Id сотрудника сборки заказов") Long employeeId,
                                              @Parameter(description = "Id сборки заказа") Long preparingOrderId);
}
