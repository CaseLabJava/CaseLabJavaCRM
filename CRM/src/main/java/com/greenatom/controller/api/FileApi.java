package com.greenatom.controller.api;

import com.greenatom.exception.message.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File API", description = "API для работы с файлами")

public interface FileApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Документ успешно загружен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Невозможно загрузить документ",
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
    ResponseEntity<Void> uploadFile(
            @Parameter(description = "Выберите подписанный документ (любой формат)")
            MultipartFile file,
            @Parameter(description = "Введите orderID, к которому хотите прикрепить документ", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Документ успешно получен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос или невозможно получить документ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Невозможно получить документ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Скачать документ"
    )
    ResponseEntity<ByteArrayResource> downloadFile(@Parameter(description = "Путь к файлу", example = "") String path,
                                                   @Parameter(description = "Типа файла", example = "") String contentType);

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Документ успешно удален"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос или невозможно удалить документ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Невозможно удалить документ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Данного документа не существует",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удалить документ"
    )
    ResponseEntity<Void> deleteFile(@Parameter(description = "Имя файла") String fileName);
}
