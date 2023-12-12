package ru.greenatom.acquiringservice.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PaymentResponseDto {
    private String cardNumber;
    private Long sumOfPay;
}
