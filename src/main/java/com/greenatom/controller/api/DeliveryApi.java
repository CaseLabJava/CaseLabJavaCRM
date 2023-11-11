package com.greenatom.controller.api;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.exception.message.DeliveryErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Delivery API", description = "API для работы с доставками")
public interface DeliveryApi {
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
                    description = "Курьер по переданному id не был найден или " +
                            "доставка не была найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Изменение статуса доставки на IN PROCESS"
    )
    ResponseEntity<Void> changeToInProcess(
            @Parameter(description = "Id доставки", example = "1")
            Long deliveryId,
            @Parameter(description = "Id курьера", example = "1")
            Long courierId
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
                    description = "Курьер по переданному id не был найден или " +
                            "доставка не была найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Изменение статуса доставки на DONE"
    )
    ResponseEntity<Void> changeToDone(
            @Parameter(description = "Id доставки", example = "1")
            Long deliveryId,
            @Parameter(description = "Id курьера", example = "1")
            Long courierId
    );
}
