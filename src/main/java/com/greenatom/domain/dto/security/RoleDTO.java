package com.greenatom.domain.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Описание модели \"Роль\"")
public class RoleDTO {

    @Schema(description = "Название роли", example = "ROLE_MANAGER")
    private String name;
}