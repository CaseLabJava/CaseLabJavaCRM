package com.greenatom.domain.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Описание модели запроса \"Сотрудник\"")
@Setter
@Getter
@AllArgsConstructor
@NamedQuery(
        name = "Employee.findEnum",
        query = "SELECT e FROM Employee e WHERE e.jobPosition = :enumValue")
public class EmployeeSearchCriteria {

    @Schema(description = "Id сотрудника", example = "1")
    private Long id;

    @Schema(description = "Имя сотрудника", example = "Дмитрий")
    private String firstname;

    @Schema(description = "Фамилия сотрудника", example = "Иванов")
    private String surname;

    @Schema(description = "Отчество сотрудника", example = "Юрьевич")
    private String patronymic;

    @Schema(description = "Логин пользователя в системе", example = "IvanovDY")
    private String username;

    @Schema(description = "Должность сотрудника", example = "MANAGER")
    private String jobPosition;

    @Schema(description = "Зарплата сотрудника", example = "100000")
    private Long salary;

    @Schema(description = "Почта сотрудника", example = "tupichok@mail.ru")
    private String email;

    @Schema(description = "Номер телефона сотрудника", example = "895436848")
    private String phoneNumber;

    @Schema(description = "Адрес сотрудника")
    private String address;
}
