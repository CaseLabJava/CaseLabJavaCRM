package ru.greenatom.acquiringservice.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "balance")
    private Long balance;
}
