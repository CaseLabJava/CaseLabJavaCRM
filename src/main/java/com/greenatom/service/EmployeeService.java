package com.greenatom.service;

import com.greenatom.domain.dto.EmployeeCleanDTO;
import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    @Transactional(readOnly = true)
    List<EmployeeCleanDTO> findAll();

    @Transactional(readOnly = true)
    Optional<EmployeeCleanDTO> findOne(Long id);

    @Transactional
    Employee save(EmployeeDTO employee);

    @Transactional
    EmployeeCleanDTO updateEmployee(EmployeeCleanDTO employee);

    @Transactional
    void deleteEmployee(Long id);

    @Transactional
    Optional<Employee> findOne(String username);
}
