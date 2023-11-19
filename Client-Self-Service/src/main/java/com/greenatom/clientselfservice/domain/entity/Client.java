package com.greenatom.clientselfservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "firstname", length = 50)
    @NonNull
    private String name;

    @Column(name = "lastname", length = 50)
    private String surname;

    @Column(name = "patronymic", length = 50)
    private String patronymic;

    @Column(name = "bank_details")
    private String bank;

    @Column(name = "inn_and_kpp")
    private String inn;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "correspondent_account")
    private String correspondentAccount;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    @NonNull
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "legal_entity")
    private Boolean legalEntity;

    @Transient
    private Set<Order> orders;

    @Transient
    private Set<Claim> claims;
}
