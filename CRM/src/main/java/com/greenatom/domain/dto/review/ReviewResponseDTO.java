package com.greenatom.domain.dto.review;

import com.greenatom.domain.enums.ReviewStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о отзыве")
public class ReviewResponseDTO {

    @Schema(description = "Id отзыва", example = "1")
    private Long id;

    @Schema(description = "Содержимое отзыва", example = "Хороший товар")
    private String content;

    @Schema(description = "Изображение отзыва")
    private byte[] image;

    @Schema(description = "Id клиента", example = "1")
    private Long clientId;

    @Schema(description = "Id позиции заказа", example = "1")
    private Long orderItemId;

    @Schema(description = "Оценка", example = "5")
    private Integer reviewMark;

    @Schema(description = "Статус отзыв", example = "CREATED")
    private ReviewStatus reviewStatus;
}
