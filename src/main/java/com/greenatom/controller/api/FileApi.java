package com.greenatom.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.ErrorMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "File API", description = "API для работы с файлами")

public interface FileApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Документ успешно загружен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос или невозможно загрузить документ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Загрузить документ"
    )
    void uploadFile(
            @Parameter(description = "Выберите подписанный документ (любой формат)")
            MultipartFile file,
            @Parameter(description = "Введите orderID, к которому хотите прикрепить документ", example = "1")
            Long id
    ) throws IOException;
}
