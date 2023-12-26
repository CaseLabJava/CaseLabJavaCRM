package ru.greenatom.acquiringservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.greenatom.acquiringservice.domain.enums.PaymentStatus;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private String email;
    private String fullName;
    private String cardNumber;
    private PaymentStatus status;
    private Long sumOfPay;
}
