package com.greenatom.PaymentService.dto.card;

import lombok.Data;

@Data
public class CardDto {
    private String number;
    private String cardholder="cardholder";
    private String expiredDate;
    private String cvv;
}
