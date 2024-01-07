package com.greenatom.PaymentService.service;


import com.greenatom.PaymentService.domain.dto.PaymentResponseDto;

public interface PaymentService {

    void createPayment(Long userId, Long orderId, Long sumOfPay);

    void initiatePayment(Long paymentId);

    PaymentResponseDto consumePaymentResult(PaymentResponseDto dto);
}
