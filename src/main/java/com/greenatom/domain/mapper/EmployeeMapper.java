package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeSearchCriteria;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.domain.entity.Employee;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper for the entity {@link Employee} and its DTO called {@link EmployeeResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeResponseDTO, Employee> {
    EmployeeResponseDTO toDto(Employee s);

    Employee toEntity(EmployeeResponseDTO s);

    Employee toEntity(CreateEmployeeRequestDTO s);

    EmployeeResponseDTO toResponse(EmployeeSearchCriteria s);

    List<EmployeeResponseDTO> toDto(Page<Employee> all);
}
