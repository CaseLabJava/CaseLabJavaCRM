package com.greenatom.PaymentService.service.impl;

import com.greenatom.PaymentService.domain.dto.CardRequestDto;
import com.greenatom.PaymentService.domain.entity.Card;
import com.greenatom.PaymentService.domain.mapper.CardMapper;
import com.greenatom.PaymentService.repository.CardRepository;
import com.greenatom.PaymentService.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Override
    @Transactional
    public void createCard(CardRequestDto cardRequestDto) {
        Card card = cardMapper.toEntity(cardRequestDto);
        cardRepository.save(card);
    }
}
