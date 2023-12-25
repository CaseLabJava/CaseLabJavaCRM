package ru.greenatom.acquiringservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Table(name = "bank_account")
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "name_of_bank")
    private String nameOfBank;
}
