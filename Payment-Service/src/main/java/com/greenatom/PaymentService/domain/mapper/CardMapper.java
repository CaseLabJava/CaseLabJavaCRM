package com.greenatom.PaymentService.domain.mapper;


import com.greenatom.PaymentService.domain.dto.CardRequestDto;
import com.greenatom.PaymentService.domain.dto.CardResponseDto;
import com.greenatom.PaymentService.domain.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CardMapper.class)
public interface CardMapper extends EntityMapper<CardResponseDto, Card> {

    Card toEntity(CardRequestDto cardRequestDto);

    CardResponseDto toDto(Card card);
}
