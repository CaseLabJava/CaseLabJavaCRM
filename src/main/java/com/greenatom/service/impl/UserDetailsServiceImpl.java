package com.greenatom.service.impl;

import com.greenatom.domain.entity.Employee;
import com.greenatom.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Request not found with username: " + username));

        return new User(
                employee.getUsername(),
                employee.getPassword(),
                new ArrayList<>(Collections.singletonList(
                        new SimpleGrantedAuthority(employee.getRole().getName())))
        );
    }
}
