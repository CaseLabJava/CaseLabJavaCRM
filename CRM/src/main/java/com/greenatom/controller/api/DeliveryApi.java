package com.greenatom.controller.api;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.exception.message.DeliveryErrorMessage;
import com.greenatom.exception.message.ErrorMessage;
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
import java.util.List;

@Tag(name = "Delivery API", description = "API для работы с доставками")
public interface DeliveryApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение доставки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Доставка по переданному id не была найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение доставки по id"
    )
    ResponseEntity<DeliveryResponseDTO> findOne(@Parameter(description = "Id доставки") Long id);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение доставки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryResponseDTO.class)
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
            summary = "Получение всех доставок"
    )
    ResponseEntity<Page<DeliveryResponseDTO>> findAll(@Parameter(description = "Начальная страницы") Integer pageNumber,
                                                      @Parameter(description = "Размер страницы") Integer pageSize,
                                                      @Parameter(description = "Id заказа") Long orderId,
                                                      @Parameter(description = "Id курьера") Long courierId,
                                                      @Parameter(
                                                              description = "Статус заказа",
                                                              in = ParameterIn.QUERY,
                                                              name = "status",
                                                              schema = @Schema(allowableValues = {
                                                                      "WAITING_FOR_DELIVERY",
                                                                      "IN_PROCESS", "DONE"}))
                                                      String deliveryStatus,
                                                      @Parameter(description = "Начало доставки")
                                                      Instant startTime,
                                                      @Parameter(description = "Конец доставки")
                                                      Instant endTime,
                                                      @Parameter(description = "Поле для сортировки")
                                                      String sortBy,
                                                      @Parameter(
                                                              in = ParameterIn.QUERY,
                                                              description = "Порядок сортировки",
                                                              name = "sortDirection",
                                                              schema = @Schema(allowableValues = {
                                                                      "ASC",
                                                                      "DESC"
                                                              }))
                                                      Sort.Direction sortDirection);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное изменение статуса",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Доставка/Курьер по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Данный курьер не может взять заказ, пока не завершить предыдущий",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Изменение статуса доставки на IN PROCESS"
    )
    ResponseEntity<Void> changeToInProcess(
            @Parameter(description = "Username курьера", example = "1")
            Principal principal,
            @Parameter(description = "Id доставки", example = "1")
            Long deliveryId
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное изменение статуса",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Доствка/Курьер по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Данный курьер не может завершить данный заказ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Изменение статуса доставки на DONE"
    )
    ResponseEntity<Void> changeToDone(
            @Parameter(description = "Username курьера", example = "1")
            Principal principal,
            @Parameter(description = "Id доставки", example = "1")
            Long deliveryId
    );
}
