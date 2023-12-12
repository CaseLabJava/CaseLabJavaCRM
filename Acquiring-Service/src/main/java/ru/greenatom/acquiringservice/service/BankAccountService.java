package ru.greenatom.acquiringservice.service;

import ru.greenatom.acquiringservice.domain.dto.PaymentResponseDto;

public interface BankAccountService {
    void consume(PaymentResponseDto dto);
}
