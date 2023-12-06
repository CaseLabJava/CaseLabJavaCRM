package com.greenatom.paymentservice.service.impl;

import com.greenatom.paymentservice.domain.dto.PaymentRequestDto;
import com.greenatom.paymentservice.domain.dto.PaymentResponseDto;
import com.greenatom.paymentservice.domain.entity.Payment;
import com.greenatom.paymentservice.domain.enums.PaymentStatus;
import com.greenatom.paymentservice.domain.mapper.PaymentMapper;
import com.greenatom.paymentservice.repository.PaymentRepository;
import com.greenatom.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponseDto pay(PaymentRequestDto paymentRequestDto) {
        Payment payment;
        Long userId = paymentRequestDto.getUserId();
        Long orderId = paymentRequestDto.getOrderId();
        payment = paymentMapper.toEntity(paymentRequestDto);
        payment.setStatus(PaymentStatus.PAYMENT_COMPLETED);
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

//    @Transactional
//    public void createPayment(Long userId, Long orderId, Long sumOfPay) {
//        paymentRepository.save(
//                Payment.builder()
//                        .userId(userId)
//                        .orderId(orderId)
//                        .date(Instant.now())
//                        .sumOfPay(sumOfPay)
//                        .status(PaymentStatus.WAITING_FOR_PAYMENT)
//                        .build());
//    }
}
