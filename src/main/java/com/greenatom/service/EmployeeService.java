package com.greenatom.service;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.domain.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeResponseDTO> findAll(Integer pagePosition, Integer pageLength);

    EmployeeResponseDTO findOne(Long id);

    EmployeeResponseDTO save(CreateEmployeeRequestDTO employee);

    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employee);

    void deleteEmployee(Long id);

    Optional<Employee> findOne(String username);
}
