package com.greenatom.controller.api;

import com.greenatom.domain.dto.ClientDTO;
import com.greenatom.utils.exception.message.ClientErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Client API", description = "API для работы с клиентами")
public interface ClientApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат клиента",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Клиент по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение клиента по id"
    )
    ResponseEntity<ClientDTO> getClient(
            @Parameter(description = "Id клиента", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат клиентов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDTO.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение всех клиентов"
    )
    ResponseEntity<List<ClientDTO>> getClientsResponse(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "", required = false) String company,
            @RequestParam(defaultValue = "", required = false) String firstname,
            @RequestParam(defaultValue = "", required = false) String lastname,
            @RequestParam(defaultValue = "", required = false) String patronymic
    );


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление клиента",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Клиент по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление клиента по id"
    )
    ResponseEntity<Void> deleteClient(
            @Parameter (description = "Id клиента", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление клиента",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Указанный клиент не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновление информации о клиенте"
    )
    ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO clientDTO);

    @ApiResponse(
            responseCode = "200",
            description = "Клиент был успешно создан",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class)
                    )
            }
    )
    @Operation(summary = "Создает Client и возвращает ClientDTO")
    ResponseEntity<ClientDTO> addClient(
            @Parameter(description = "Client Request")
            ClientDTO clientDTO
    );
}
