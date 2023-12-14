package com.greenatom.paymentservice.service.impl;

import com.greenatom.paymentservice.domain.dto.PaymentResponseDto;
import com.greenatom.paymentservice.domain.entity.Card;
import com.greenatom.paymentservice.domain.entity.Payment;
import com.greenatom.paymentservice.domain.enums.PaymentStatus;
import com.greenatom.paymentservice.domain.mapper.PaymentMapper;
import com.greenatom.paymentservice.repository.CardRepository;
import com.greenatom.paymentservice.repository.PaymentRepository;
import com.greenatom.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final KafkaTemplate<Long, PaymentResponseDto> kafkaTemplate;
    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public void createPayment(Long clientId, Long orderId, Long sumOfPay) {
        // TODO: Добавить exception
        Card card = cardRepository.findCardByClientId(clientId).orElseThrow();
        Payment payment = savePayment(paymentMapper.mapToPayment(card, orderId, sumOfPay));
        initiatePayment(payment.getId());
    }

    private Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void initiatePayment(Long paymentId) {
        // TODO: Exceptions
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        PaymentResponseDto dto = paymentMapper.toDto(payment);
        log.info("sending {}", dto.toString());
        kafkaTemplate.send("payment", dto);
    }

    @KafkaListener(topics = "payment-result-topic", groupId = "payment-service-group")
    @Transactional
    public PaymentResponseDto consumePaymentResult(PaymentResponseDto dto) {
        log.info("consume: {}", dto);
        updatePaymentStatus(dto);
        return dto;
    }

    private void updatePaymentStatus(PaymentResponseDto dto) {
        PaymentStatus paymentStatus = dto.getStatus();
        // TODO: Тут по идее должен быть switch case для разных статусов, но пока так
        if (paymentStatus == PaymentStatus.PAYMENT_COMPLETED) {
            confirmPayment(dto);
        } else {
            cancelPayment(dto);
        }
    }

    private void confirmPayment(PaymentResponseDto dto) {
        paymentRepository.updatePaymentStatusById(dto.getId(), dto.getStatus());
    }

    private void cancelPayment(PaymentResponseDto dto) {
        paymentRepository.updatePaymentStatusById(dto.getId(), dto.getStatus());
        // TODO: Логика отказа???
    }
}
