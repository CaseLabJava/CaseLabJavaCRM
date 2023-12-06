package com.greenatom.paymentservice.domain.mapper;


import com.greenatom.paymentservice.domain.dto.PaymentRequestDto;
import com.greenatom.paymentservice.domain.dto.PaymentResponseDto;
import com.greenatom.paymentservice.domain.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentResponseDto, Payment> {
    PaymentResponseDto toDto(Payment s);
    Payment toEntity(PaymentRequestDto s);
}

