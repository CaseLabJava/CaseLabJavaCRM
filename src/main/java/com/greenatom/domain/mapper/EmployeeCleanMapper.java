package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.entity.Employee;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Employee} and its DTO called {@link CreateEmployeeRequestDTO}.
 */

@Mapper(componentModel = "spring")
public interface EmployeeCleanMapper extends EntityMapper<CreateEmployeeRequestDTO, Employee> {
    CreateEmployeeRequestDTO toDto(Employee s);

    Employee toEntity(CreateEmployeeRequestDTO s);

}
