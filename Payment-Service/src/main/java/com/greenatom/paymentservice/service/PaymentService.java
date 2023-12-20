package com.greenatom.paymentservice.service;


import com.greenatom.paymentservice.domain.dto.PaymentResponseDto;

public interface PaymentService {

    void createPayment(Long userId, Long orderId, Long sumOfPay);

    void initiatePayment(Long paymentId);

    PaymentResponseDto consumePaymentResult(PaymentResponseDto dto);
}
