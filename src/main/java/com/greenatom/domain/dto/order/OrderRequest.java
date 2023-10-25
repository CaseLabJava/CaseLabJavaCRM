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
@Schema(description = "Класс для создания Order")
public class OrderRequest {

    @Schema(description = "Информация о клиенте")
    private Long clientId;

    @Schema(description = "Информация о менеджере")
    private Long employeeId;
}
