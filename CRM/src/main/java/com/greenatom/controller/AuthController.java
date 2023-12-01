package com.greenatom.controller;

import com.greenatom.controller.api.AuthApi;
import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.security.AuthDTO;
import com.greenatom.domain.dto.security.JwtResponse;
import com.greenatom.domain.dto.security.RefreshJwtRequest;
import com.greenatom.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<JwtResponse> registration(@RequestBody CreateEmployeeRequestDTO employeeRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.registration(employeeRequestDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthDTO authDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(authDto));
    }

    @PostMapping("/access-token")
    public ResponseEntity<JwtResponse> getAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.getAccessToken(refreshJwtRequest.getRefreshToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.refresh(refreshJwtRequest.getRefreshToken()));
    }
}
