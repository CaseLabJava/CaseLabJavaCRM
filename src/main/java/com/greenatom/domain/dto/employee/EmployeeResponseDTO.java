package com.greenatom.domain.dto.employee;

import com.greenatom.domain.dto.security.RoleDTO;
import com.greenatom.domain.enums.JobPosition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the Employee.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Описание модели ответа \"Сотрудник\"")
public class EmployeeResponseDTO {

    @Schema(description = "Id сотрудника", example = "1")
    private Long id;

    @Schema(description = "Имя сотрудника", example = "Дмитрий")
    private String firstname;

    @Schema(description = "Фамилия сотрудника", example = "Пучков")
    private String surname;

    @Schema(description = "Отчество сотрудника", example = "Юрьевич")
    private String patronymic;

    @Schema(description = "Должность сотрудника", example = "MANAGER")
    private JobPosition jobPosition;

    @Schema(description = "Зарплата сотрудника", example = "100000")
    private Long salary;

    @Schema(description = "Почта сотрудника", example = "tupichok@mail.ru")
    private String email;

    @Schema(description = "Номер телефона сотрудника", example = "895436848")
    private String phoneNumber;

    @Schema(description = "Имя пользователя сотрудника", example = "Puchkov_D_Y_1")
    private String username;

    @Schema(description = "Роль сотрудника")
    private RoleDTO role;

    @Schema(description = "Адрес сотрудника")
    private String address;
}

