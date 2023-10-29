package com.greenatom.service.impl;

import com.greenatom.domain.dto.EmployeeCleanDTO;
import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.mapper.EmployeeCleanMapper;
import com.greenatom.domain.mapper.EmployeeMapper;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.repository.RoleRepository;
import com.greenatom.service.EmployeeService;
import com.greenatom.utils.exception.EmailAlreadyUsedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeServiceImpl является сервисом для работы с сотрудниками. Он использует базу данных для доступа к информации
 * о сотрудниках и ролях, преобразует их в формат DTO и возвращает список сотрудников или конкретного сотрудника
 * по его ID.
 *
 * @version 1.0
 * @autor Максим Быков, Андрей Начевный
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    private final EmployeeCleanMapper employeeCleanMapper;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public List<EmployeeCleanDTO> findAll(Integer pagePosition, Integer pageLength) {
        log.debug("Request to get all Employees");
        return employeeCleanMapper.toDto(employeeRepository.findAll(
                PageRequest.of(pagePosition, pageLength)));
    }

    @Override
    public Optional<EmployeeCleanDTO> findOne(Long id) {
        log.debug("Order to get Employee : {}", id);
        return Optional.ofNullable(employeeCleanMapper.toDto(employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("An employee with this ID was not found: " + id))));
    }

    @Override
    public Employee save(EmployeeDTO employeeDTO) {
        List<Employee> existingUsers = employeeRepository.findAll();
        log.debug("Order to save employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setPassword(encoder.encode(employeeDTO.getPassword()));
        employee.setUsername(generateUsername(employeeDTO));
        employee.setRole(roleRepository.findByName(employeeDTO.getRole().getName()).orElse(null));
        for (Employee e : existingUsers) {
            if ((e.getEmail().equals(employee.getEmail()))) {
                throw new EmailAlreadyUsedException();
            }
        }
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public EmployeeCleanDTO updateEmployee(EmployeeCleanDTO employee) {
        log.debug("Order to partially update Employee : {}", employee);
        return employeeRepository
                .findById(employee.getId())
                .map(existingEvent -> {
                    employeeCleanMapper.partialUpdate(existingEvent, employee);

                    return existingEvent;
                })
                .map(employeeRepository::save)
                .map(employeeCleanMapper::toDto).orElseThrow(
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

    @Override
    public Optional<Employee> findOne(String username) {
        return employeeRepository.findByUsername(username);
    }

    private String generateUsername(EmployeeDTO employeeDTO) {
        StringBuilder username = new StringBuilder();
        username.append(employeeDTO.getFirstname()).append("_")
                .append(employeeDTO.getSurname().charAt(0)).append("_")
                .append(employeeDTO.getPatronymic().charAt(0));
        Integer countOfUsernameInDb = employeeRepository.countByUsername(username.toString());
        if (countOfUsernameInDb > 0) {
            username.append("_").append((countOfUsernameInDb + 1));
        }
        return username.toString();
    }
}
