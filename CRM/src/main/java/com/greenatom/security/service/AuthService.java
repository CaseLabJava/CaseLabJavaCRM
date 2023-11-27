package com.greenatom.security.service;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.security.AuthDTO;
import com.greenatom.domain.dto.security.JwtResponse;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.entity.Role;
import com.greenatom.domain.mapper.EmployeeMapper;
import com.greenatom.exception.AuthException;
import com.greenatom.security.jwt.JwtCore;
import com.greenatom.service.impl.EmployeeServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * AuthService является компонентом Spring и использует другие компоненты, такие как JwtCore и AuthenticationManager.
 *
 * <p>Регистрация: Метод registration принимает объект EmployeeDTO и создает нового сотрудника с помощью
 * EmployeeServiceImpl. Затем генерируются токены доступа и обновления с помощью jwtCore, ивозвращается объект
 * JwtResponse, содержащий эти токены.
 *
 * <p>Вход: Метод login принимает объект AuthDto, содержащий имя пользователя и пароль. AuthenticationManager пытается
 * аутентифицировать пользователя, и в случае успешного входа генерируются токены доступа и обновления, как и при
 * регистрации. Затем возвращается объект JwtResponse с токенами.
 *
 * @author Андрей Начевный
 * @version 1.0
 */

@Component
@RequiredArgsConstructor
public class AuthService {

    private final JwtCore jwtCore;
    private final AuthenticationManager authenticationManager;
    private final EmployeeServiceImpl employeeService;
    private final EmployeeMapper employeeMapper;

    public JwtResponse registration(CreateEmployeeRequestDTO createEmployeeRequestDTO) {
        if (!Objects.equals(createEmployeeRequestDTO.getPassword(), createEmployeeRequestDTO.getRepeatPassword())) {
            throw AuthException.CODE.INVALID_REPEAT_PASSWORD.get();
        }
        Employee employee = employeeMapper.toEntity(employeeService.save(createEmployeeRequestDTO));
        String accessToken = jwtCore.generateAccessToken(employee);
        String refreshToken = jwtCore.generateRefreshToken(employee);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse login(AuthDTO authDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDto.getUsername(), authDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw AuthException.CODE.NO_SUCH_USERNAME_OR_PWD.get();
        }
        final Employee employee = employeeService
                .findOne(authDto.getUsername())
                .orElseThrow(AuthException.CODE.NO_SUCH_USERNAME_OR_PWD::get);
        final String accessToken = jwtCore.generateAccessToken(employee);
        final String refreshToken = jwtCore.generateRefreshToken(employee);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse getAccessToken(String refreshToken) {
        return generateAccessTokenOrRefresh(refreshToken, "getAccessToken");
    }

    public JwtResponse refresh(String refreshToken) {
        return generateAccessTokenOrRefresh(refreshToken, "refresh");
    }

    private JwtResponse generateAccessTokenOrRefresh(String refreshToken, String action) {
        if (jwtCore.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtCore.extractRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String roleName = (String) claims.get("role");
            final Integer id = (Integer) claims.get("employee_id");
            employeeService.findOne(Long.valueOf(id));
            Role role = new Role();
            role.setName(roleName);
            Employee employeeForJwt = new Employee();
            employeeForJwt.setUsername(username);
            employeeForJwt.setRole(role);
            employeeForJwt.setId(Long.valueOf(id));
            final String accessToken = jwtCore.generateAccessToken(employeeForJwt);
            if (action.equals("refresh")) {
                final String newRefreshToken = jwtCore.generateRefreshToken(employeeForJwt);
                return new JwtResponse(accessToken, newRefreshToken);
            } else {
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }
}
