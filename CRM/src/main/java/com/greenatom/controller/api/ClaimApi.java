package com.greenatom.controller.api;

import com.greenatom.domain.dto.claim.ClaimCreationDTO;
import com.greenatom.domain.dto.claim.ClaimRequestDTO;
import com.greenatom.domain.dto.claim.ClaimResponseDTO;
import com.greenatom.exception.message.ClaimErrorMessage;
import com.greenatom.exception.message.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Tag(name = "Claim API", description = "API для работы с жалобами")
public interface ClaimApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат жалобы",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ClaimResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Жалоба по переданному id не был найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ClaimErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение жалобы по id"
    )
    ResponseEntity<ClaimResponseDTO> getClaim(
            @Parameter(description = "Id жалобы", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат жалоб",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ClaimResponseDTO.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение всех жалоб"
    )
    ResponseEntity<List<ClaimResponseDTO>> getClaimsResponse(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize);


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление жалобы",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ClaimResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "жалоба по переданному id не был найдена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ClaimErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление жалобы по id"
    )
    void deleteClaim(
            @Parameter (description = "Id жалобы", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление жалобы",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ClaimResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Указанный жалоба не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ClaimErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновление информации о жалобе"
    )
    ResponseEntity<ClaimResponseDTO> updateClaim(
            @Parameter(description = "Id жалобы")
            Long id,
            @Parameter(description = "Информация о жалобе")
            ClaimRequestDTO claimRequestDTO
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Жалоба была успешно создана",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Нет заказа по переданному id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
    })
    @Operation(summary = "Создает Claim и возвращает ClaimDTO")
    ResponseEntity<ClaimResponseDTO> addClaim(
            @Parameter(description = "Информация о жалобе")
            ClaimCreationDTO claimRequestDTO
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Жалоба была успешно назначена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Нет заказа по переданному id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Назначает жалобу на сотрудника")
    ClaimResponseDTO appointClaim(@Parameter(description = "ID жалобы") Long claim,
                                  @Parameter(description = "ID работника") Principal principal);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Вердикт успешно вынесен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Нет заказа по переданному id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Сотрудник не имеет прав разрешить данную жалобу",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(summary = "Вынесение вердикта по жалобе")
    ClaimResponseDTO resolveClaim(@Parameter(description = "Id жалобы") Long claimId,
                                  @Parameter(description = "Id сотрудника") Principal principal,
                                  @Parameter(
                                          in = ParameterIn.QUERY,
                                          description = "Статус жалобы",
                                          schema = @Schema(allowableValues = {
                                                  "RESOLVED_FOR_CLIENT",
                                                  "RESOLVED_FOR_COMPANY"
                                          })) String status);
}
