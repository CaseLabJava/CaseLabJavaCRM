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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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
        Payment payment = paymentMapper.mapToPayment(card, orderId, sumOfPay);
        paymentRepository.save(payment);
//        produce(paymentMapper.toDto(payment));
    }

    public void produce(Long paymentId) {
        // TODO: Exception
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        PaymentResponseDto dto = paymentMapper.toDto(payment);
        System.out.println(dto.getCardNumber() + dto.getSumOfPay());
        log.info("sending {}", dto.toString());
        kafkaTemplate.send("payment", dto);
    }
}
