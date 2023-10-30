package com.greenatom.service.impl;

import com.greenatom.domain.dto.EmployeeCleanDTO;
import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.mapper.EmployeeCleanMapper;
import com.greenatom.domain.mapper.EmployeeMapper;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.repository.RoleRepository;
import com.greenatom.service.EmployeeService;
import com.greenatom.utils.exception.AuthException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @author Максим Быков, Андрей Начевный
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    private final EmployeeCleanMapper employeeCleanMapper;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public List<EmployeeCleanDTO> findAll(Integer pagePosition, Integer pageLength) {
        return employeeCleanMapper.toDto(employeeRepository.findAll(
                PageRequest.of(pagePosition, pageLength)));
    }

    @Override
    public EmployeeCleanDTO findOne(Long id) {
        return employeeCleanMapper.toDto(employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("An employee with this ID was not found: " + id)));
    }

    @Override
    public Employee save(EmployeeDTO employeeDTO) {
        List<Employee> existingUsers = employeeRepository.findAll();
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setPassword(encoder.encode(employeeDTO.getPassword()));
        employee.setUsername(generateUsername(employeeDTO));
        employee.setRole(roleRepository.findByName(employeeDTO.getRole().getName()).orElse(null));
        for (Employee e : existingUsers) {
            if ((e.getEmail().equals(employee.getEmail()))) {
                throw AuthException.CODE.EMAIL_IN_USE.get();
            }
        }
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public EmployeeCleanDTO updateEmployee(EmployeeCleanDTO employee) {
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
                .ifPresent(employeeRepository::delete);
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
