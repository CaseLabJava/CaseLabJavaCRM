package com.greenatom.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

/**
 * A Client
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
    @Size(min = 1, max = 200)
    private String company;

    @Column(name = "firstname", length = 50)
    @NonNull
    @Size(min = 1, max = 50)
    private String name;

    @Column(name = "lastname", length = 50)
    @NonNull
    @Size(min = 1, max = 50)
    private String surname;

    @Column(name = "patronymic", length = 50)
    @NonNull
    @Size(min = 1, max = 50)
    private String patronymic;

    @Column(name = "bank_details")
    @NotBlank
    @Size(min = 3, max = 20)
    private String bank;

    @Column(name = "inn_and_kpp")
    @NotBlank
    @Size(min = 13, max = 13)
    private String inn;

    @Column(name = "ogrn")
    @NotBlank
    @Size(min = 13, max = 13)
    private String ogrn;

    @Column(name = "correspondent_account")
    @NotBlank
    @Size(min = 20, max = 20)
    private String correspondentAccount;

    @Column(name = "address")
    @Size(min = 1, max = 200)
    private String address;


    @Column(name = "phone_number")
    @NotBlank
    @Size(min = 1, max = 20)
    private String phoneNumber;

    @Email
    @Size(min = 5, max = 254)
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "client")
    private Set<Order> orders;

    @OneToMany(mappedBy = "client")
    private Set<Claim> claims;

    public String getFullName() {
        return String.format("%s %s %s", surname, name, patronymic);
    }

}
