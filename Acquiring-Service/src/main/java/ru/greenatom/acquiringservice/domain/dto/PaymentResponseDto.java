package ru.greenatom.acquiringservice.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.greenatom.acquiringservice.domain.enums.PaymentStatus;

@Data
@RequiredArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private String cardNumber;
    private PaymentStatus status;
    private Long sumOfPay;
}
