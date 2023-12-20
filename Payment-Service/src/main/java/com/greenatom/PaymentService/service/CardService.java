package com.greenatom.paymentservice.service;

import com.greenatom.paymentservice.domain.dto.CardRequestDto;

public interface CardService {
    void createCard(CardRequestDto cardRequestDto);
}
