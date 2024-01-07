package com.greenatom.PaymentService.service;


import com.greenatom.PaymentService.domain.dto.CardRequestDto;

public interface CardService {
    void createCard(CardRequestDto cardRequestDto);
}
