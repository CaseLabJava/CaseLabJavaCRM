package com.greenatom.service;

import com.greenatom.domain.dto.employee.*;
import com.greenatom.domain.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeResponseDTO> findAll(EntityPage entityPage, EmployeeSearchCriteria employeeSearchCriteria);

    EmployeeResponseDTO findOne(Long id);

    EmployeeResponseDTO save(CreateEmployeeRequestDTO employee);

    EmployeeResponseDTO updateEmployee(Long id, EmployeeSearchCriteria employee);

    void deleteEmployee(Long id);

    Optional<Employee> findOne(String username);
}
