package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.EmployeeCleanDTO;
import com.greenatom.domain.entity.Employee;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper for the entity {@link Employee} and its DTO called {@link EmployeeCleanDTO}.
 */

@Mapper(componentModel = "spring")
public interface EmployeeCleanMapper extends EntityMapper<EmployeeCleanDTO, Employee> {
    EmployeeCleanDTO toDto(Employee s);

    Employee toEntity(EmployeeCleanDTO s);

    List<EmployeeCleanDTO> toDto(Page<Employee> all);
}
