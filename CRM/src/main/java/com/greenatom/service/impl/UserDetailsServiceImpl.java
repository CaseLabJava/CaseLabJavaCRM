package com.greenatom.service.impl;

import com.greenatom.domain.entity.Employee;
import com.greenatom.exception.AuthException;
import com.greenatom.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Этот код обеспечивает аутентификацию пользователей в приложении.
 * Метод loadUserByUsername ищет информацию о пользователе с помощью репозитория EmployeeRepository и создаёт
 * объект UserDetails в случае успеха. Если пользователь не найден, метод бросает исключение
 * UsernameNotFoundException.
 * @author Андрей Начевный
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository
                .findByUsername(username)
                .orElseThrow(AuthException.CODE.NO_SUCH_USERNAME_OR_PWD::get);
        return new User(
                employee.getUsername(),
                employee.getPassword(),
                new ArrayList<>(Collections.singletonList(
                        new SimpleGrantedAuthority(employee.getRole().getName())))
        );
    }
}
