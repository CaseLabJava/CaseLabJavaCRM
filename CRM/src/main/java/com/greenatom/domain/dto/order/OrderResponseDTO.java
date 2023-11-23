package com.greenatom.domain.dto.order;

import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.domain.enums.DeliveryType;
import com.greenatom.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * A DTO for the Order.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Описание класса Order")
public class OrderResponseDTO {

    @Schema(description = "Id заказа")
    private Long id;

    @Schema(description = "Информация о клиенте")
    private ClientResponseDTO client;

    @Schema(description = "Информация о сотруднике")
    private EmployeeResponseDTO employee;

    @Schema(description = "Ссылка на директорию с документами")
    private String linkToFolder;

    @Schema(description = "Дата заказа")
    private Instant orderDate;

    @Schema(description = "Статус заказа")
    private OrderStatus orderStatus;

    @Schema(description = "Тип доставки")
    private DeliveryType deliveryType;
}
