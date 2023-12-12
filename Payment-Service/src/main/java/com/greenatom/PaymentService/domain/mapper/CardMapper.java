package com.greenatom.paymentservice.domain.mapper;

import com.greenatom.paymentservice.domain.dto.CardRequestDto;
import com.greenatom.paymentservice.domain.dto.CardResponseDto;
import com.greenatom.paymentservice.domain.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CardMapper.class)
public interface CardMapper extends EntityMapper<CardResponseDto, Card> {

    Card toEntity(CardRequestDto cardRequestDto);

    CardResponseDto toDto(Card card);
}
