package com.greenatom.controller.api;

import com.greenatom.domain.dto.review.ReviewRequestDTO;
import com.greenatom.domain.dto.review.ReviewResponseDTO;
import com.greenatom.exception.message.ReviewErrorMessage;
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
                                    schema = @Schema(implementation = ReviewErrorMessage.class)
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
                    responseCode = "200",
                    description = "Успешный возврат отзывов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    contentSchema = @Schema(implementation = ReviewResponseDTO.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение всех отзывов"
    )
    ResponseEntity<Page<ReviewResponseDTO>> findAll(
            @Parameter(description = "Начальная страница") Integer pagePosition,
            @Parameter(description = "Размер страницы") Integer pageSize,
            @Parameter(description = "Содержимое отзыва") String content,
            @Parameter(description = "Изображение отзыва") byte[] image,
            @Parameter(description = "Id клиента") Long clientId,
            @Parameter(description = "Оценка") Integer reviewMark,
            @Parameter(description = "Статус отзыва") String reviewStatus,
            @Parameter(description = "Id позиции заказа") Long orderItemId,
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
                                    schema = @Schema(implementation = ReviewErrorMessage.class)
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
                                    schema = @Schema(implementation = ReviewErrorMessage.class)
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
