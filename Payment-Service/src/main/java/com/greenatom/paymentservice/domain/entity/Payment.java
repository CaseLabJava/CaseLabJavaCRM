package com.greenatom.paymentservice.domain.entity;

import com.greenatom.paymentservice.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "sum_of_pay", nullable = false)
    private Long sumOfPay;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
