package com.greenatom.clientselfservice.contoller.api;

import com.greenatom.clientselfservice.domain.dto.security.AuthDTO;
import com.greenatom.clientselfservice.domain.dto.security.ClientRegistrationDTO;
import com.greenatom.clientselfservice.domain.dto.security.JwtResponse;
import com.greenatom.clientselfservice.domain.dto.security.RefreshJwtRequest;
import com.greenatom.clientselfservice.utils.exception.message.ClientErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

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
                                    schema = @Schema(implementation = ClientErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь по переданному username не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientErrorMessage.class)
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
    ResponseEntity<JwtResponse> registration(@RequestBody ClientRegistrationDTO clientRegistrationDTO,
                                             BindingResult bindingResult);


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
                                    schema = @Schema(implementation = ClientErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Возвращает обновленные jwt и refresh токены")
    ResponseEntity<JwtResponse> refresh(@RequestBody RefreshJwtRequest refreshJwtRequest);

}