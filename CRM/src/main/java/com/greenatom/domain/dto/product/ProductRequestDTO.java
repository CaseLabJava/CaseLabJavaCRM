package com.greenatom.domain.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о продукте")
public class ProductRequestDTO {

    @Schema(description = "Имя продукта", example = "Гвоздь")
    private String productName;

    @Schema(description = "Единица измерения продукта", example = "штука")
    private String unit;

    @Schema(description = "Общее количество продукта", example = "1000")
    private Long storageAmount;

    @Schema(description = "Цена за единицу продукта", example = "3")
    private Long cost;

    @Schema(description = "Изображение товара")
    private byte[] image;
}
