package com.greenatom.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Класс для запроса загрузки документа")
public class UploadDocDTO {

    @Schema(description = "Id заказа")
    private Long id;

    @Schema(description = "Полный путь к документу")
    private String linkToFolder;

    private String fileName;

}
