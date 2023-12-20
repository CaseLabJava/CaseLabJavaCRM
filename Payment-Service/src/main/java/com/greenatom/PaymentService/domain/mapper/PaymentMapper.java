package com.greenatom.paymentservice.domain.mapper;


import com.greenatom.paymentservice.domain.dto.PaymentRequestDto;
import com.greenatom.paymentservice.domain.dto.PaymentResponseDto;
import com.greenatom.paymentservice.domain.entity.Card;
import com.greenatom.paymentservice.domain.entity.Payment;
import com.greenatom.paymentservice.domain.enums.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.Instant;

@Mapper(componentModel = "spring", uses = PaymentMapper.class, imports = {Instant.class, PaymentStatus.class})
public interface PaymentMapper extends EntityMapper<PaymentResponseDto, Payment> {

    @Mapping(target = "cardNumber", source = "card.cardNumber")
    PaymentResponseDto toDto(Payment s);
    Payment toEntity(PaymentRequestDto s);

    @Mappings({
            @Mapping(target = "card", source = "card"),
            @Mapping(target = "orderId", source = "orderId"),
            @Mapping(target = "sumOfPay", source = "sumOfPay"),
            @Mapping(target = "date", expression = "java(Instant.now())"),
            @Mapping(target = "status", expression = "java(PaymentStatus.WAITING_FOR_PAYMENT)")
    })
    Payment mapToPayment(Card card, Long orderId, Long sumOfPay);
}

