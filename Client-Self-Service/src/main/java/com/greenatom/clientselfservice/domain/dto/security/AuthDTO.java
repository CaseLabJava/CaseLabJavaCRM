package com.greenatom.clientselfservice.domain.dto.security;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {

    @Email
    private String email;

    private String password;
}
