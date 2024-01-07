package com.greenatom.PaymentService.service.impl;


import com.greenatom.PaymentService.domain.dto.PaymentResponseDto;
import com.greenatom.PaymentService.domain.entity.Card;
import com.greenatom.PaymentService.domain.entity.Payment;
import com.greenatom.PaymentService.domain.mapper.PaymentMapper;
import com.greenatom.PaymentService.exception.PaymentException;
import com.greenatom.PaymentService.repository.CardRepository;
import com.greenatom.PaymentService.repository.PaymentRepository;
import com.greenatom.PaymentService.service.PaymentService;
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
        Card card = cardRepository.findCardByClientId(clientId).orElseThrow(
                PaymentException.CODE.NO_SUCH_CARD::get);
        Payment payment = savePayment(paymentMapper.mapToPayment(card, orderId, sumOfPay));
        initiatePayment(payment.getId());
    }

    private Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void initiatePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                PaymentException.CODE.NO_SUCH_PAYMENT::get);
        PaymentResponseDto dto = paymentMapper.toDto(payment);
        log.info("sending {}", dto.toString());
        kafkaTemplate.send("payment", dto);
    }

    @KafkaListener(topics = "payment-result-topic", groupId = "payment-service-group")
    @Transactional
    public PaymentResponseDto consumePaymentResult(PaymentResponseDto dto) {
        log.info("consuming: {}", dto);
        updatePaymentStatus(dto);
        return dto;
    }

    private void updatePaymentStatus(PaymentResponseDto dto) {
        paymentRepository.updatePaymentStatusById(dto.getId(), dto.getStatus());
    }
}
