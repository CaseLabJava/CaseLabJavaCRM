package com.greenatom.PaymentService.dto.payment;

import lombok.Data;

import java.time.Instant;

@Data
public class PaymentDto {
    private Long userId;
    private Long orderId;
    private Instant date;
    private Long sumOfPay;
    private String card_number;
}
