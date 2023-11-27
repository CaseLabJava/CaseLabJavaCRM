package com.greenatom.domain.dto.orderitem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Описание продукта в заказе")
public class OrderItemSearchCriteria {
    @Schema(description = "id продукта в заказе", example = "1")
    private Long id;

    @Schema(description = "Связь с заказом")
    private Long orderId;

    @Schema(description = "Связь с продуктом")
    private Long productId;

    @Schema(description = "Название продукта в заказе")
    private String name;

    @Schema(description = "Количесвто продукта в заказе")
    private String unit;

    @Schema(description = "Цена продукта в заказе")
    private Long cost;

    @Schema(description = "Запрошеное количество продукта в заказе")
    private Long orderAmount;
}
