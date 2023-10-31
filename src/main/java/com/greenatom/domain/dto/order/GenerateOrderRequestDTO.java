package com.greenatom.domain.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Класс для запроса генерации документа заявки")
public class GenerateOrderRequestDTO {

    @Schema(description = "Id заказа")
    Long id;
}
