package com.greenatom.controller.api;

import com.greenatom.domain.dto.product.ProductRequestDTO;
import com.greenatom.domain.dto.product.ProductResponseDTO;
import com.greenatom.exception.message.ErrorMessage;
import com.greenatom.exception.message.ProductErrorMessage;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Product API - это интерфейс, который описывает набор методов для работы с продуктами. Он включает методы
 * для сохранения новых продуктов, получения уже существующих, удаление и их обновление.
 * @autor Степан Моргачев
 * @version 1.0
 */

@Tag(name = "Product API", description = "API для работы с продуктами")
public interface ProductApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат продукта",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =
                                            ProductErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение продукта по id"
    )
    ResponseEntity<ProductResponseDTO> getProduct(
            @Parameter(description = "Id продукта", example = "5")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат продуктов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный порядок сортировки",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение продуктов с фильтрацией по названию и стоимости"
    )
    ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @Parameter(description = "Позиция страницы") Integer pagePosition,
            @Parameter(description = "Длина страницы") Integer pageLength,
            @Parameter(description = "Название продукта") String productName,
            @Parameter(description = "Единица измерения") String unit,
            @Parameter(description = "Количество продукта на складе") Long storageAmount,
            @Parameter(description = "Стоимость продукта") Long cost,
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
                    responseCode = "200",
                    description = "Успешное обновление продукта",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Указанный продукт не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновление информации о продукте"
    )
    ResponseEntity<ProductResponseDTO> updateProduct(
            @Parameter(description = "Id продукта")
            Long id,
            @Parameter(description = "Информация о продукте")
            @RequestBody ProductRequestDTO product);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное сохранение продукта",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDTO.class)
                            )
                    }
            )
    })
    @Operation(summary = "Создает Product и возвращает ProductDTO")
    ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO product);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное удаление продукта",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Продукт по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Продукт по переданному id находится в заказе и не может быть удален",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление продукта по id"
    )
    ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Удентификатор удаляемого продукта", example = "1")
            Long id
    );
}
