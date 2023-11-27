package com.greenatom.clientselfservice.service.impl;

import com.greenatom.clientselfservice.domain.entity.Client;
import com.greenatom.clientselfservice.repository.ClientRepository;
import com.greenatom.clientselfservice.utils.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Client client = clientRepository.findByEmail(username).orElseThrow(AuthException.CODE.NO_SUCH_USERNAME_OR_PWD::get);

        return new User(client.getEmail(),
                        client.getPassword(),
                        new ArrayList<>(Collections.singletonList
                                (new SimpleGrantedAuthority("ROLE_USER"))));
    }
}
