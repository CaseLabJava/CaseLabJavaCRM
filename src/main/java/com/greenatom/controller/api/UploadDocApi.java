package com.greenatom.controller.api;


import com.greenatom.domain.dto.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.ErrorMessage;

import org.springframework.web.multipart.MultipartFile;

@Tag(name = "UploadDoc API", description = "API для загрузки подписанных документов")
public interface UploadDocApi {

    @ApiResponse(
            responseCode = "200",
            description = "Документ успешно загружен",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)
                    )
            }
    )
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
    @Operation(
            summary = "Загрузить документ"
    )
    void uploadFile(
            @Parameter(description = "Выберете подписанный документ (любой формат)")
            MultipartFile file,
            @Parameter(description = "Введите orderID, к которому хотите прикрепить документ", example = "1")
            Long id


    );

}

