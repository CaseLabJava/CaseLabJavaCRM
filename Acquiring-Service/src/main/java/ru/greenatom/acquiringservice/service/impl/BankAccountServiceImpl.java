package ru.greenatom.acquiringservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.greenatom.acquiringservice.domain.dto.PaymentResponseDto;
import ru.greenatom.acquiringservice.domain.entity.BankAccount;
import ru.greenatom.acquiringservice.domain.enums.PaymentStatus;
import ru.greenatom.acquiringservice.exception.AcquiringException;
import ru.greenatom.acquiringservice.repository.BankAccountRepository;
import ru.greenatom.acquiringservice.service.BankAccountService;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final KafkaTemplate<Long, PaymentResponseDto> kafkaTemplate;

    @Override
    @Transactional
    @KafkaListener(topics = "payment", groupId = "consumerServer")
    public void consume(PaymentResponseDto dto) {
        log.info("consuming: {}", dto);
        validatePayment(dto);
        dto.setStatus(PaymentStatus.PAYMENT_COMPLETED);
        sendPaymentResult(dto);
    }

    private void sendPaymentResult(PaymentResponseDto dto) {
        log.info("sending: {}", dto);
        kafkaTemplate.send("payment-result-topic", dto);
    }

    private void validatePayment(PaymentResponseDto dto) {
        BankAccount bankAccount = bankAccountRepository
                .findBankAccountByCardNumber(dto.getCardNumber())
                .orElseThrow(AcquiringException.CODE.BANK_ACCOUNT_NOT_FOUND::get);
        long currBalance = bankAccount.getBalance();
        if (currBalance < dto.getSumOfPay()) {
            throw AcquiringException.CODE.NOT_ENOUGH_BALANCE.get();
        }
        bankAccount.setBalance(currBalance - dto.getSumOfPay());
        bankAccountRepository.save(bankAccount);
    }
}
