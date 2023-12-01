package com.greenatom.PaymentService.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "number", unique = true, nullable = false, length = 19)
    private String number;

    @Column(name = "cardholder", length = 64)
    private String cardholder;

    @Column(name = "expired_date", nullable = false)
    private String expiredDate;

    @Column(name = "cvv", nullable = false)
    private String cvv;
}
