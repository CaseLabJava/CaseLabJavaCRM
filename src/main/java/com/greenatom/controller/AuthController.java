package com.greenatom.controller;

import com.greenatom.controller.api.AuthApi;
import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.security.AuthDTO;
import com.greenatom.domain.dto.security.JwtResponse;
import com.greenatom.domain.dto.security.RefreshJwtRequest;
import com.greenatom.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Этот код представляет собой контроллер для обработки запросов к API аутентификации. Он включает в себя следующие
 * методы:
 *
 * <p>– /signUp- Метод обработки запроса на регистрацию пользователя. Принимает объект EmployeeDTO и возвращает ответ
 * в формате JwtResponse.
 * <p>– /signIn - Метод обработки запроса на вход пользователя. Принимает объект AuthDto и возвращает ответ в формате
 * JwtResponse, содержащий токен доступа.
 * <p>– /accessToken - Метод получения токена доступа на основе входящего запроса RefreshJwtRequest.
 * <p>– /refresh - Этот метод не определен в данном коде. Он, вероятно, должен обновлять токен доступа на основе
 * полученного запроса.
 *
 * @author Андрей Начевный, Максим Быков, Даниил Змаев
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController implements AuthApi {

    private final AuthService authService;

    @PostMapping("/signup")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public JwtResponse registration(@RequestBody CreateEmployeeRequestDTO employeeRequestDTO) {
        return authService.registration(employeeRequestDTO);
    }

    @PostMapping("/signin")
    public JwtResponse login(@RequestBody AuthDTO authDto) {
        return authService.login(authDto);
    }

    @PostMapping("/access-token")
    public JwtResponse getAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return authService.getAccessToken(refreshJwtRequest.getRefreshToken());
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return authService.refresh(refreshJwtRequest.getRefreshToken());
    }
}
