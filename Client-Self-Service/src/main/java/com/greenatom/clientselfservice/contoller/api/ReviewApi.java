package com.greenatom.clientselfservice.contoller.api;

import com.greenatom.clientselfservice.domain.dto.review.ReviewRequestDTO;
import com.greenatom.clientselfservice.domain.dto.review.ReviewResponseDTO;
import com.greenatom.clientselfservice.utils.exception.message.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Review API", description = "API для работы с отзывами")
public interface ReviewApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат отзыва",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReviewResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Отзыв по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение отзыва по id"
    )
    ResponseEntity<ReviewResponseDTO> findOne(
            @Parameter(description = "Id отзыва", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное удаление отзыва",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReviewResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Отзыв по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление отзыва по id"
    )
    ResponseEntity<Void> deleteReview(
            @Parameter (description = "Id отзыва", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление отзыва",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReviewResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Указанный отзыв не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновление информации об отзыве"
    )
    ResponseEntity<ReviewResponseDTO> updateReview(
            @Parameter(description = "Id отзыва")
            Long id,
            @Parameter(description = "Информация об отзыве")
            ReviewRequestDTO clientRequestDTO
    );

    @ApiResponse(
            responseCode = "200",
            description = "Отзыв был успешно создан",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponseDTO.class)
                    )
            }
    )
    @Operation(summary = "Создает Review и возвращает ReviewDTO")
    ResponseEntity<ReviewResponseDTO> addReview(
            @Parameter(description = "Информация об отзыве")
            ReviewRequestDTO clientRequestDTO
    );
}