package com.greenatom.controller.api;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.security.AuthDTO;
import com.greenatom.domain.dto.security.JwtResponse;
import com.greenatom.domain.dto.security.RefreshJwtRequest;
import com.greenatom.exception.message.EmployeeErrorMessage;
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
 * Auth API - это интерфейс, который описывает набор методов для аутентификации пользователей. Он включает методы
 * для регистрации новых пользователей, входа в существующий аккаунт и обновления информации о пользователе.
 * Интерфейс AuthApi содержит аннотацию @ApiResponses, которая определяет ответы сервера на различные запросы.
 * В данном случае, успешный вход в аккаунт возвращает код ответа 200 и тело ответа в формате JSON, содержащее
 * объект JwtResponse.
 *
 * <p>JwtResponse - это класс, который представляет собой ответ на запрос входа. Он содержит информацию о токене
 * доступа и сроке его действия. Этот класс реализует интерфейс Response и имеет аннотацию @Schema, которая указывает
 * на то, что это схема ответа.
 *
 * <p>Кроме того, AuthApi имеет аннотацию @Tag, которая позволяет добавлять метаданные к API. В данном случае,
 * она используется для указания названия и описания API.
 * @author Даниил Змаев
 * @version 1.0
 */

@Tag(name = "Auth API", description = "API для входа в систему")
public interface AuthApi {

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный вход в аккаунт",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные учетные данные пользователя",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь по переданному username не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возвращает jwt и refresh токены")
    ResponseEntity<JwtResponse> login(
            @Parameter(description = "Имя пользователя и пароль")
            AuthDTO authDto
            );


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная регистрация аккаунта",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtResponse.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возвращает jwt и refresh токены")
    ResponseEntity<JwtResponse> registration(@RequestBody CreateEmployeeRequestDTO employeeRequestDTO);


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение accessTocken`а",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtResponse.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возвращает accessToken и refreshToken = null")
    ResponseEntity<JwtResponse> getAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный refresh",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Неверный username в refreshToken",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возвращает обновленные jwt и refresh токены")
    ResponseEntity<JwtResponse> refresh(@RequestBody RefreshJwtRequest refreshJwtRequest);

}
