package com.greenatom.domain.dto;

import com.greenatom.domain.enums.JobPosition;
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
public class EmployeeDTO {
    private Long id;

    private String name;

    private String surname;

    private String patronymic;

    private JobPosition jobPosition;

    private String salary;

    private String address;

    private String phoneNumber;

    private String password;

    private String username;

    private RoleDto role;
}
