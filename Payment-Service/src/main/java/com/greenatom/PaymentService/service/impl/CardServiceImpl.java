package com.greenatom.paymentservice.service.impl;

import com.greenatom.paymentservice.domain.dto.CardRequestDto;
import com.greenatom.paymentservice.domain.entity.Card;
import com.greenatom.paymentservice.domain.mapper.CardMapper;
import com.greenatom.paymentservice.repository.CardRepository;
import com.greenatom.paymentservice.service.CardService;
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
