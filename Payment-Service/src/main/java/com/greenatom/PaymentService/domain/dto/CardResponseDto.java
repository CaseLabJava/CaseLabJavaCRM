package com.greenatom.paymentservice.domain.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class CardResponseDto {
    private Long id;
    private Long clientId;
    private String cardNumber;
    private String cardholder="cardholder";
    private Instant expiredDate;
    private String cvv;
    private List<PaymentResponseDto> payments;

}
