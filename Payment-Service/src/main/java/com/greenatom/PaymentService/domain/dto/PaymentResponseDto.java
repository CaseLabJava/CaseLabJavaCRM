package com.greenatom.paymentservice.domain.dto;

import com.greenatom.paymentservice.domain.enums.PaymentStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private Long userId;
    private Long orderId;
    private Instant date;
    private Long sumOfPay;
    private PaymentStatus status;
}
