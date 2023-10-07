package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.entity.Employee;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Employee} and its DTO called {@link EmployeeDTO}.
 */

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    EmployeeDTO toDto(Employee s);

    Employee toEntity(EmployeeDTO s);
}
