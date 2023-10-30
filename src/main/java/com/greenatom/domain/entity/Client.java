package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * A Client.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
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
    @NonNull
    private String surname;

    @Column(name = "patronymic", length = 50)
    @NonNull
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

    @OneToMany(mappedBy = "client")
    private Set<Order> orders;

    public String getFullName() {
        return String.format("%s %s %s", surname, name, patronymic);
    }
}
