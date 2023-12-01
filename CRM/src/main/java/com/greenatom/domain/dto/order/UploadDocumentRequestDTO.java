package com.greenatom.domain.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Класс для запроса загрузки документа")
public class UploadDocumentRequestDTO {

    @Schema(description = "Id заказа")
    private Long id;

    @Schema(description = "Подписанный документ")
    MultipartFile file;

    @Schema(description = "Абсолютный путь к файлу")
    private String linkToFolder;

    public UploadDocumentRequestDTO(MultipartFile file, Long id) {
        this.file = file;
        this.id = id;
    }
}
