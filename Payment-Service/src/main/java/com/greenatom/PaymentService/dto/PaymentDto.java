package com.greenatom.PaymentService.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDto {
    private String number;
    private String cardholder="cardholder";
    private String expiredDate;
    private String cvv;
}
