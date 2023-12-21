package com.greenatom.domain.entity;

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
public class Client {

    private Long id;

    @Size(min = 1, max = 200)
    private String company;

    @NonNull
    @Size(min = 1, max = 50)
    private String name;

    @NonNull
    @Size(min = 1, max = 50)
    private String surname;

    @NonNull
    @Size(min = 1, max = 50)
    private String patronymic;

    @NotBlank
    @Size(min = 3, max = 20)
    private String bank;

    @NotBlank
    @Size(min = 13, max = 13)
    private String inn;

    @NotBlank
    @Size(min = 13, max = 13)
    private String ogrn;

    @NotBlank
    @Size(min = 20, max = 20)
    private String correspondentAccount;

    @Size(min = 1, max = 200)
    private String address;


    @NotBlank
    @Size(min = 1, max = 20)
    private String phoneNumber;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private Set<Order> orders;

    private Set<Claim> claims;

//    @OneToMany(mappedBy = "client")
//    private Set<Review> reviews;

    public String getFullName() {
        return String.format("%s %s %s", surname, name, patronymic);
    }

}
