package com.greenatom.service;

import com.greenatom.domain.dto.EmployeeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    @Transactional(readOnly = true)
    List<EmployeeDTO> findAll();

    @Transactional(readOnly = true)
    Optional<EmployeeDTO> findOne(Long id);

    @Transactional
    EmployeeDTO save(EmployeeDTO employee);

    @Transactional
    EmployeeDTO updateEmployee(EmployeeDTO employee);

    @Transactional
    void deleteEmployee(Long id);
}
