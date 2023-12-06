package com.greenatom.paymentservice.domain.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CardResponseDto {
    private String number;
    private String cardholder="cardholder";
    private Instant expiredDate;
    private String cvv;
}
