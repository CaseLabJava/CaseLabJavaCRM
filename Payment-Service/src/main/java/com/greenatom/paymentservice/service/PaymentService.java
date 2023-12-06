package com.greenatom.paymentservice.service;

import com.greenatom.paymentservice.domain.dto.PaymentRequestDto;
import com.greenatom.paymentservice.domain.dto.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto pay(PaymentRequestDto paymentRequestDto);

//    void createPayment(Long userId, Long orderId, Long sumOfPay);
}
