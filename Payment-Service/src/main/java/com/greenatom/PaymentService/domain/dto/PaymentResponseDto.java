package com.greenatom.paymentservice.domain.dto;

import com.greenatom.paymentservice.domain.enums.PaymentStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private String cardNumber;
    private PaymentStatus status;
    private Long sumOfPay;
}
