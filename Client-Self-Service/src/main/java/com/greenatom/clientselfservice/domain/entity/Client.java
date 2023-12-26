package com.greenatom.clientselfservice.domain.entity;

import com.greenatom.clientselfservice.domain.enums.ClientSource;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter

public class Client {
    private Long id;

    private String company;

    @NonNull
    private String name;

    private String surname;

    private String patronymic;

    private String bank;

    private String inn;

    private String ogrn;

    private String correspondentAccount;

    private String address;

    private String phoneNumber;

    @NonNull
    private String email;

    private String password;

    private Boolean legalEntity;

    @NotNull
    private ClientSource clientSource;

    private Set<Order> orders;

    private Set<Claim> claims;
}
