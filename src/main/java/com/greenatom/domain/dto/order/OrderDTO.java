package com.greenatom.domain.dto.order;

import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * A DTO for the Order.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Описание класса Order")
public class OrderDTO {

    @Schema(description = "Id заказа")
    private Long id;

    @Schema(description = "Информациыя о клиенте")
    private ClientResponseDTO client;

    @Schema(description = "Информация о сотруднике")
    private EmployeeDTO employee;

    @Schema(description = "Ссылка на дерикторию с документами")
    private String linkToFolder;

    @Schema(description = "Дата заказа")
    private Date orderDate;

    @Schema(description = "Статус заказа")
    private OrderStatus orderStatus;
}
