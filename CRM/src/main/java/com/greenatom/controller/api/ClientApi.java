package com.greenatom.controller.api;

import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.exception.message.ClientErrorMessage;
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

@Tag(name = "Client API", description = "API для работы с клиентами")
public interface ClientApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат клиента",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientResponseDTO.class)
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
    ResponseEntity<ClientResponseDTO> findOne(
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
                                    contentSchema = @Schema(implementation = ClientResponseDTO.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение всех клиентов"
    )
    ResponseEntity<Page<ClientResponseDTO>> findAll(
            @Parameter(description = "Начальная страница") Integer pagePosition,
            @Parameter(description = "Размер страницы") Integer pageSize,
            @Parameter(description = "Имя клиента") String firstname,
            @Parameter(description = "Фамилия клиента") String lastname,
            @Parameter(description = "Отчество клиента") String patronymic,
            @Parameter(description = "Адресс клиента") String address,
            @Parameter(description = "Название банка клиента") String bank,
            @Parameter(description = "Название компании клиента") String company,
            @Parameter(description = "Корреспондентский счет клиента") String correspondentAccount,
            @Parameter(description = "ИНН клиента") String inn,
            @Parameter(description = "ОГРН клиента") String ogrn,
            @Parameter(description = "Номер телефона клиента") String phoneNumber,
            @Parameter(description = "Электронная почта клиента") String email,
            @Parameter(description = "Поле для сортировки") String sortBy,
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
                    description = "Успешное удаление клиента",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientResponseDTO.class)
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
                                    schema = @Schema(implementation = ClientResponseDTO.class)
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
    ResponseEntity<ClientResponseDTO> updateClient(
            @Parameter(description = "Id клиента")
            Long id,
            @Parameter(description = "Инфомрация о клиенте")
            ClientRequestDTO clientRequestDTO
    );

    @ApiResponse(
            responseCode = "200",
            description = "Клиент был успешно создан",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientResponseDTO.class)
                    )
            }
    )
    @Operation(summary = "Создает Client и возвращает ClientDTO")
    ResponseEntity<ClientResponseDTO> addClient(
            @Parameter(description = "Инфомрация о клиенте")
            ClientRequestDTO clientRequestDTO
    );
}
