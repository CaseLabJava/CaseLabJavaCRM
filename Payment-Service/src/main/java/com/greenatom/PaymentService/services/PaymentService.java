package com.greenatom.PaymentService.services;

import com.greenatom.PaymentService.dto.payment.PaymentDto;
import com.greenatom.PaymentService.entities.Payment;
import com.greenatom.PaymentService.mappers.PaymentMapper;
import com.greenatom.PaymentService.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    public Payment pay(PaymentDto paymentDto) {
        Payment payment;
        Long userId = paymentDto.getUserId();
        Long orderId = paymentDto.getOrderId();

        if(paymentRepository.existsByUserIdAndOrderId(userId, orderId)) {
            payment = paymentRepository
                    .findByUserIdAndOrderId(userId, orderId)
                    .orElseThrow();
        }
        else {
            payment = paymentMapper.toEntity(paymentDto);
        }
        return paymentRepository.save(payment);
    }
}
