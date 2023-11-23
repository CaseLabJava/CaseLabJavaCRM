package com.greenatom.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PreparingOrderStatus {
    @JsonProperty("WAITING_FOR_PREPARING")
    WAITING_FOR_PREPARING,

    @JsonProperty("IN_PROCESS")
    IN_PROCESS,

    @JsonProperty("DONE")
    DONE
}
