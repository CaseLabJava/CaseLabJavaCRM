package com.greenatom.PaymentService.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "sum_of_pay", nullable = false)
    private Long sumOfPay;

    @Column(name = "status")
    private String status;
}
