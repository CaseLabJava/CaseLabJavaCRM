package com.greenatom.PaymentService.mappers;


import com.greenatom.PaymentService.dto.payment.PaymentDto;
import com.greenatom.PaymentService.entities.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDto, Payment> {
    PaymentDto toDto(Payment s);
    Payment toEntity(PaymentDto s);
}

