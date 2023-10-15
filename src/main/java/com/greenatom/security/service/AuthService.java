package com.greenatom.security.service;

import com.greenatom.domain.dto.AuthDto;
import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.dto.JwtResponse;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.entity.Role;
import com.greenatom.security.jwt.JwtCore;
import com.greenatom.service.impl.EmployeeServiceImpl;
import com.greenatom.utils.exception.AuthException;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final JwtCore jwtCore;
    private final AuthenticationManager authenticationManager;
    private final EmployeeServiceImpl employeeServiceImpl;


    public JwtResponse registration(EmployeeDTO employeeDTO){
        Employee employee = employeeServiceImpl.save(employeeDTO);
        String accessToken = jwtCore.generateAccessToken(employee);
        String refreshToken = jwtCore.generateRefreshToken(employee);
        return new JwtResponse(accessToken,refreshToken);
    }

    public JwtResponse login(AuthDto authDto){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDto.getUsername(),authDto.getPassword()
                    )
            );
        } catch (BadCredentialsException e){
            throw new AuthException("Incorrect username or password");
        }
        final Employee employee = employeeServiceImpl.findOne(authDto.getUsername()).get();
        final String accessToken = jwtCore.generateAccessToken(employee);
        final String refreshToken = jwtCore.generateRefreshToken(employee);
        return new JwtResponse(accessToken,refreshToken);
    }

    public JwtResponse getAccessToken(String refreshToken){
        return generateAccessTokenOrRefresh(refreshToken,"getAccessToken");
    }

    public JwtResponse refresh(String refreshToken){
        return generateAccessTokenOrRefresh(refreshToken,"refresh");
    }

    private JwtResponse generateAccessTokenOrRefresh(String refreshToken, String action){
        if(jwtCore.validateRefreshToken(refreshToken)){
            final Claims claims = jwtCore.extractRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String roleName = (String) claims.get("role");

            if(employeeServiceImpl.findOne(username).isEmpty()){
                throw new EntityNotFoundException("Employee with this username does not exists");
            }

            Role role = new Role();
            role.setName(roleName);
            Employee employeeForJwt = new Employee();
            employeeForJwt.setUsername(username);
            employeeForJwt.setRole(role);
            final String accessToken = jwtCore.generateAccessToken(employeeForJwt);
            if (action.equals("refresh")){
                final String newRefreshToken = jwtCore.generateRefreshToken(employeeForJwt);
                return new JwtResponse(accessToken,newRefreshToken);
            } else {
                return new JwtResponse(accessToken,null);
            }
        }
        return new JwtResponse(null,null);
    }

}
