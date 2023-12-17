package com.greenatom.clientselfservice.domain.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
