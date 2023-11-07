package com.greenatom.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum PreparingOrderStatus {
    @Schema(type = "String", allowableValues = {"WAITING_FOR_PREPARING", "IN_PROCESS", "DONE"})
    WAITING_FOR_PREPARING,
    IN_PROCESS,
    DONE
}
