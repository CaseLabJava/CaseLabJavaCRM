package com.greenatom.domain.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Класс для модерации отзыва сотрудником")
public class ReviewRedactDTO {

    @Schema(description = "Содержимое отзыва", example = "Хороший товар")
    private String content;

    @Schema(description = "Изображение отзыва")
    private byte[] image;


}
