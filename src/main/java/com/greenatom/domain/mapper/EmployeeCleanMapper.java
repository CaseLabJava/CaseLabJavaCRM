package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.EmployeeCleanDTO;
import com.greenatom.domain.entity.Employee;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Employee} and its DTO called {@link EmployeeCleanDTO}.
 */

@Mapper(componentModel = "spring")
public interface EmployeeCleanMapper extends EntityMapper<EmployeeCleanDTO, Employee> {
    EmployeeCleanDTO toDto(Employee s);

    Employee toEntity(EmployeeCleanDTO s);
}
