package com.greenatom.service;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.domain.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    @Transactional(readOnly = true)
    List<EmployeeResponseDTO> findAll(Integer pagePosition, Integer pageLength);

    @Transactional(readOnly = true)
    EmployeeResponseDTO findOne(Long id);

    @Transactional
    EmployeeResponseDTO save(CreateEmployeeRequestDTO employee);

    @Transactional
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employee);

    @Transactional
    void deleteEmployee(Long id);

    @Transactional
    Optional<Employee> findOne(String username);
}
