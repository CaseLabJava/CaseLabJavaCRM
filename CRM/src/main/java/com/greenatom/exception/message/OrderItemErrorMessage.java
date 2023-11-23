package com.greenatom.exception.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record OrderItemErrorMessage(

        @Schema(description = "Код ошибки", example = "NO_SUCH_ORDER_ITEM")
        String code,
        @Schema(description = "Сообщение ошибки", example = "No order item with such id")
        String message

) {
}