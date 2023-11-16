package com.greenatom.controller.api;

import com.greenatom.domain.dto.employee.EmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.exception.message.EmployeeErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Employee API", description = "API для работы с сотрудниками")
public interface EmployeeApi {
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат сотрудника",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сотрудник по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение сотрудника по id"
    )
    ResponseEntity<EmployeeResponseDTO> getEmployee(
            @Parameter(description = "Id сотрудника", example = "1")
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный возврат сотрудника",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeResponseDTO.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Получение всех сотрудников"
    )
    ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(
            @Parameter(description = "Позиция страницы", example = "0")
            Integer pagePosition,
            @Parameter(description = "Длина страницы", example = "5")
            Integer pageLength
    );


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Успешное удаление сотрудника",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Сотрудник по переданному id не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Удаление сотрудника по id"
    )
    ResponseEntity<Void> deleteEmployee(
            @Parameter
            Long id
    );

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обновление сотрудника",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Указанный сотрудник не был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeErrorMessage.class)
                            )
                    }
            )
    })
    @Operation(
            summary = "Обновление информации о сотруднике"
    )
    ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @Parameter(description = "Id сотрудника")
            Long id,
            @Parameter(description = "Информация о сотруднике")
            @RequestBody EmployeeRequestDTO employee);
}
