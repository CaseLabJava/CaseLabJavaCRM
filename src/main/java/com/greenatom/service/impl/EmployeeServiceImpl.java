package com.greenatom.service.impl;

import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.mapper.EmployeeMapper;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.repository.RoleRepository;
import com.greenatom.service.EmployeeService;
import com.greenatom.utils.exception.EmailAlreadyUsedException;
import com.greenatom.utils.exception.UsernameAlreadyUsedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

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
    public Employee save(EmployeeDTO employeeDTO) {
        List<Employee> existingUsers = employeeRepository.findAll();
        log.debug("Request to save employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setPassword(encoder.encode(employeeDTO.getPassword()));
        employee.setUsername(generateUsername(employeeDTO));
        employee.setRole(roleRepository.findByName(employeeDTO.getRole().getName()).orElse(null));
        employeeRepository.save(employee);
        for (Employee e :
                existingUsers) {
            if (Objects.equals(e.getEmail(), employee.getEmail())) {
                throw new EmailAlreadyUsedException();
            }
            if (Objects.equals(e.getUsername(), employee.getUsername())) {
                throw new UsernameAlreadyUsedException();
            }
        }
        return employee;
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

    @Override
    public Optional<Employee> findOne(String username) {
        return employeeRepository.findByUsername(username);
    }

    private String generateUsername(EmployeeDTO employeeDTO){
        StringBuilder username = new StringBuilder();
         return username.append(employeeDTO.getName()).append("_")
                 .append(employeeDTO.getSurname().charAt(0)).append("_")
                 .append(employeeDTO.getPatronymic().charAt(0))
                 .append("_")
                 .append(employeeRepository.count() + 1)
                 .toString();
    }
}
