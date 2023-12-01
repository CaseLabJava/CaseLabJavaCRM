package com.greenatom.domain.dto.order;

import com.greenatom.domain.dto.orderitem.OrderItemRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Класс для создания Order")
public class OrderRequestDTO {

    @Schema(description = "Информация о клиенте")
    private Long clientId;

    @Schema(description = "Список продуктов для заказа")
    private List<OrderItemRequestDTO> orderItemList;
}
