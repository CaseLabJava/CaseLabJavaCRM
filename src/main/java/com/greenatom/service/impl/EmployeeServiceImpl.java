package com.greenatom.service.impl;

import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.mapper.EmployeeMapper;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDTO> findAll() {
        log.debug("Request to get all Employees");
        return employeeMapper.toDto(employeeRepository.findAll());
    }

    @Override
    public Optional<EmployeeDTO> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return Optional.ofNullable(employeeMapper.toDto(employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Request not found with id: " + id))));
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employee) {
        log.debug("Request to partially update Employee : {}", employee);
        return employeeRepository
                .findById(employee.getId())
                .map(existingEvent -> {
                    employeeMapper.partialUpdate(existingEvent, employee);

                    return existingEvent;
                })
                .map(employeeRepository::save)
                .map(employeeMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository
                .findById(id)
                .ifPresent(employee -> {
                    employeeRepository.delete(employee);
                    log.debug("Deleted Employee: {}", employee);
                });
    }
}
