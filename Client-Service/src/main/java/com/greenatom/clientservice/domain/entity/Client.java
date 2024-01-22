package com.greenatom.clientservice.domain.entity;

import com.greenatom.clientservice.domain.enums.ClientSource;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "client")
@NoArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "firstname", length = 50)
    @NonNull
    private String firstname;

    @Column(name = "lastname", length = 50)
    private String lastname;

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
    @Email
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "legal_entity")
    private Boolean legalEntity;

    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private ClientSource clientSource;
}