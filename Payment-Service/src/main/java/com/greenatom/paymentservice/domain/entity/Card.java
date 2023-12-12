package com.greenatom.paymentservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "number", unique = true, nullable = false, length = 19)
    private String cardNumber;

    @Column(name = "cardholder", length = 64)
    private String cardholder;

    @Column(name = "expired_date", nullable = false)
    private Instant expiredDate;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @OneToMany(mappedBy = "card")
    private List<Payment> payments;
}
