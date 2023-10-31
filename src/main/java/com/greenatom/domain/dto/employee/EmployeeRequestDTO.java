package com.greenatom.domain.dto.employee;

import com.greenatom.domain.dto.RoleDTO;
import com.greenatom.domain.enums.JobPosition;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Описание модели запроса \"Сотрудник\"")
public class EmployeeRequestDTO {

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

    @Schema(description = "Роль сотрудника")
    private RoleDTO role;

    @Schema(description = "Адрес сотрудника")
    private String address;
}
