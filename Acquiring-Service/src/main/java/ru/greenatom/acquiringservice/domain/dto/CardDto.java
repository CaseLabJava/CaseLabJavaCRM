package ru.greenatom.acquiringservice.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class CardDto extends AbstractDto {
    private String number;
    private String cardholder="cardholder";
    private Instant expiredDate;
    private String cvv;
}
