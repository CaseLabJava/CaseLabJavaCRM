package com.greenatom.service;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.domain.dto.employee.EmployeeSearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface EmployeeService {

    Page<EmployeeResponseDTO> findAll(EntityPage entityPage, EmployeeSearchCriteria employeeSearchCriteria);

    EmployeeResponseDTO findOne(Long id);

    EmployeeResponseDTO findEmployeeByUsername(String username);

    EmployeeResponseDTO save(CreateEmployeeRequestDTO employee);

    EmployeeResponseDTO updateEmployee(Long id, EmployeeSearchCriteria employee);

    void deleteEmployee(Long id);

    Optional<Employee> findOne(String username);
}
