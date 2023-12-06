package com.greenatom.paymentservice.domain.dto;

import lombok.Data;

import java.time.Instant;
@Data
public class CardRequestDto {
    private String number;
    private String cardholder="cardholder";
    private Instant expiredDate;
    private String cvv;
}
