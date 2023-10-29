package com.greenatom.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Username и Password")
public class AuthDTO {

    @Schema(description = "username")
    private String username;

    @Schema(description = "password")
    private String password;
}
