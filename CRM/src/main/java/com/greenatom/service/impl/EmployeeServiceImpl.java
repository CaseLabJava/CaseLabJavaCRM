package com.greenatom.service.impl;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.employee.EmployeeResponseDTO;
import com.greenatom.domain.dto.employee.EmployeeSearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.entity.Courier;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.enums.JobPosition;
import com.greenatom.domain.mapper.EmployeeMapper;
import com.greenatom.exception.AuthException;
import com.greenatom.exception.EmployeeException;
import com.greenatom.repository.CourierRepository;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.repository.RoleRepository;
import com.greenatom.repository.criteria.EmployeeCriteriaRepository;
import com.greenatom.service.EmployeeService;
import com.greenatom.utils.mapper.TranslateRusToEng;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeServiceImpl является сервисом для работы с сотрудниками. Он использует базу данных для доступа к информации
 * о сотрудниках и ролях, преобразует их в формат DTO и возвращает список сотрудников или конкретного сотрудника
 * по его ID.
 *
 * @author Максим Быков, Андрей Начевный
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeCriteriaRepository employeeCriteriaRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final CourierRepository courierRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDTO> findAll(EntityPage entityPage, EmployeeSearchCriteria employeeSearchCriteria) {
        return employeeCriteriaRepository.findAllWithFilters(entityPage, employeeSearchCriteria).map(employeeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO findOne(Long id) {
        return employeeMapper.toDto(employeeRepository
                .findById(id)
                .orElseThrow(EmployeeException.CODE.NO_SUCH_EMPLOYEE::get));
    }

    @Override
    @Transactional
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
        if (employee.getJobPosition().equals(JobPosition.COURIER)) {
            Courier courier = new Courier();
            courier.setEmployee(employee);
            courier.setIsActive(true);
            courierRepository.save(courier);
        }
        employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeSearchCriteria employee) {
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
    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository
                .findById(id)
                .ifPresent(employeeRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO findEmployeeByUsername(String username) {
        return employeeMapper.toDto(employeeRepository
                .findByUsername(username)
                .orElseThrow(() -> EmployeeException.CODE.NO_SUCH_EMPLOYEE.get(username)));
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
