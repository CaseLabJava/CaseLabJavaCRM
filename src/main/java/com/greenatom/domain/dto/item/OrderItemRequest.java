package com.greenatom.domain.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Класс для создания OrderItem")
public class OrderItemRequest {

    @Schema(description = "Id продукта")
    Long productId;

    @Schema(description = "Количество продукта в заказе")
    Long orderAmount;
}
