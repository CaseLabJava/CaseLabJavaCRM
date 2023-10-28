package com.greenatom.controller.api;

import com.greenatom.domain.dto.ProductDTO;
import com.greenatom.utils.exception.message.ProductErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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
                                    schema = @Schema(implementation = ProductDTO.class)
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
    ProductDTO getProduct(
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
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение продуктов с фильтрацией по названию и стоимости"
    )
    List<ProductDTO> getAllProducts(
            @Parameter(description = "Позиция страницы", example = "0")
            Integer pagePosition,
            @Parameter(description = "Длина страницы", example = "5")
            Integer pageLength,
            @Parameter(description = "Подстрока в названии продукта", example = "s")
            String name,
            @Parameter(description = "Стоимость продукта должна быть меньше указанного", example = "2147483647")
            Integer cost
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление продукта",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
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
    ProductDTO updateProduct(@RequestBody ProductDTO product);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное сохранение продукта",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    }
            )
    })
    @Operation(summary = "Создает Product и возвращает ProductDTO")
    ProductDTO addProduct(@RequestBody ProductDTO product);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление продукта",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
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
            )
    })
    @Operation(
            summary = "Удаление продукта по id"
    )
    void deleteProduct(
            @Parameter(description = "Удентификатор удаляемого продукта", example = "1")
            Long id
    );
}
