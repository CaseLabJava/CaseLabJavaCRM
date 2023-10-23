package com.greenatom.controller.api;

import com.greenatom.domain.dto.RequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Request API - это интерфейс, который описывает набор методов для работы с заявками. Он включает методы
 * для сохранения новых заявок, получения уже существующих заявок и их обновление.
 * Интерфейс AuthApi содержит аннотацию @ApiResponses, которая определяет ответы сервера на различные запросы.
 * В данном случае, успешный вход в аккаунт возвращает код ответа 200 и тело ответа в формате JSON, содержащее
 * объект RequestDTO.
 * @autor Даниил Змаев
 * @version 1.0
 */
@Tag(name = "Request API", description = "API для работы с заявками")
public interface RequestApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное сохранение заявки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RequestDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "501",
                    description = "Ошибка сохранения заявки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RequestDTO.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возращает RequestDTO")
    RequestDTO addRequest(
            @Parameter(description = "RequestDTO")
            RequestDTO requestDTO
    );
}
