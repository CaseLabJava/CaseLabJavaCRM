package com.greenatom.clientselfservice.domain.dto.review;


import com.greenatom.clientselfservice.domain.enums.ReviewStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

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

    @Schema(description = "Статус отзыва", example = "CREATED")
    private ReviewStatus reviewStatus;

    @Schema(description = "Время создания претензии", example = "2016-01-06T15:22:53.403Z")
    private Instant creationTime;
}
