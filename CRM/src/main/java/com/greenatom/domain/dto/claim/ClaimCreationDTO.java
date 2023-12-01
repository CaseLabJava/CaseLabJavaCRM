package com.greenatom.domain.dto.claim;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimCreationDTO {
    @Schema(description = "Id заказа", example = "1")
    private Long orderId;

    @Schema(description = "Описание жалобы", example = "Товар разбит")
    @NotBlank(message = "Description should not be blank")
    private String description;
}
