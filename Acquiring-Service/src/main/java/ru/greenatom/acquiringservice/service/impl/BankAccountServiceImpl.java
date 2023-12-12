package ru.greenatom.acquiringservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.greenatom.acquiringservice.domain.dto.PaymentResponseDto;
import ru.greenatom.acquiringservice.repository.BankAccountRepository;
import ru.greenatom.acquiringservice.service.BankAccountService;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Override
    @KafkaListener(topics = "payment", groupId = "consumerServer")
    public void consume(PaymentResponseDto dto) {
        System.out.println("\n\n\n" + dto.toString());
    }
}
