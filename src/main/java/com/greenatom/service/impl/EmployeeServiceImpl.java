package com.greenatom.service.impl;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.mapper.EmployeeMapper;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.repository.RoleRepository;
import com.greenatom.service.EmployeeService;
import com.greenatom.utils.exception.AuthException;
import com.greenatom.utils.exception.EmployeeException;
import com.greenatom.utils.mapper.TranslateRusToEng;
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

    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public List<EmployeeResponseDTO> findAll(Integer pagePosition, Integer pageLength) {
        return employeeMapper.toDto(employeeRepository.findAll(
                PageRequest.of(pagePosition, pageLength)));
    }

    @Override
    public EmployeeResponseDTO findOne(Long id) {
        return employeeMapper.toDto(employeeRepository
                .findById(id)
                .orElseThrow(EmployeeException.CODE.NO_SUCH_EMPLOYEE::get));
    }

    @Override
    public EmployeeResponseDTO save(CreateEmployeeRequestDTO employeeResponseDTO) {
        List<Employee> existingUsers = employeeRepository.findAll();
        Employee employee = employeeMapper.toEntity(employeeResponseDTO);
        employee.setPassword(encoder.encode(employeeResponseDTO.getPassword()));
        employee.setUsername(generateUsername(employeeResponseDTO));
        employee.setRole(roleRepository.findByName(employeeResponseDTO.getRole().getName()).orElse(null));
        for (Employee e : existingUsers) {
            if ((e.getEmail().equals(employee.getEmail()))) {
                throw AuthException.CODE.EMAIL_IN_USE.get();
            }
        }
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employee) {
        return employeeRepository
                .findById(id)
                .map(existingEvent -> {
                    employeeMapper.partialUpdate(existingEvent, employeeMapper.toResponse(employee));

                    return existingEvent;
                })
                .map(employeeRepository::save)
                .map(employeeMapper::toDto)
                .orElseThrow(EmployeeException.CODE.NO_SUCH_EMPLOYEE::get);
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

    private String generateUsername(CreateEmployeeRequestDTO employeeRequest) {
        StringBuilder username = new StringBuilder();
        String firstname = TranslateRusToEng.translateFromRusToEng(employeeRequest.getFirstname());
        String surname = TranslateRusToEng.translateFromRusToEng(employeeRequest.getSurname());
        String patronymic = TranslateRusToEng.translateFromRusToEng(employeeRequest.getPatronymic());
        username.append(surname)
                .append(firstname.charAt(0))
                .append(patronymic.charAt(0));
        Integer countOfUsernameInDb = employeeRepository.countByUsername(username.toString());
        if (countOfUsernameInDb > 0) {
            username.append("_").append((countOfUsernameInDb + 1));
        }
        return username.toString();
    }
}
