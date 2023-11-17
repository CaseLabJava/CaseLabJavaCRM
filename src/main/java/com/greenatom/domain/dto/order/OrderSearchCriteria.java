package com.greenatom.domain.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Schema(description = "Описание модели запроса \"Заказ\"")
@Setter
@Getter
@AllArgsConstructor
@NamedQuery(
        name = "Order.findEnum",
        query = "SELECT o FROM Order o WHERE o.orderStatus = :enumValue")
public class OrderSearchCriteria {

    @Schema(description = "Id заказа")
    private Long id;

    @Schema(description = "Информация о клиенте")
    private Long client;

    @Schema(description = "Информация о сотруднике")
    private Long employee;

    @Schema(description = "Ссылка на директорию с документами")
    private String linkToFolder;

    @Schema(description = "Дата заказа")
    private Instant orderDate;

    @Schema(description = "Статус заказа")
    private String orderStatus;

    @Schema(description = "Тип доставки")
    private String deliveryType;
}
