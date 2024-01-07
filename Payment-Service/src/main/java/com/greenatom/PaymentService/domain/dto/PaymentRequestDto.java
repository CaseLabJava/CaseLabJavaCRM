package com.greenatom.PaymentService.domain.dto;


import com.greenatom.PaymentService.domain.enums.PaymentStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Data
public class PaymentRequestDto {
    private Long userId;
    private Long orderId;
    private Instant date;
    private Long sumOfPay;
    private PaymentStatus status;
}
