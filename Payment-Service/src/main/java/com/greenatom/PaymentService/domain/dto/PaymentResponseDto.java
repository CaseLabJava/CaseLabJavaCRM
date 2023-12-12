package com.greenatom.paymentservice.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class PaymentResponseDto {
    private String cardNumber;
    private Long sumOfPay;
}
