package com.greenatom.controller;

import com.greenatom.domain.dto.AuthDto;
import com.greenatom.domain.dto.EmployeeDTO;
import com.greenatom.domain.dto.JwtResponse;
import com.greenatom.domain.dto.RefreshJwtRequest;
import com.greenatom.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public JwtResponse registration(@RequestBody EmployeeDTO employeeDTO){
        return authService.registration(employeeDTO);
    }

    @GetMapping("/signIn")
    public JwtResponse login(@RequestBody AuthDto authDto){
        return authService.login(authDto);
    }

    @GetMapping("/accessToken")
    public JwtResponse getAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest){
        return authService.getAccessToken(refreshJwtRequest.getRefreshToken());
    }

    @GetMapping("/refresh")
    public JwtResponse refresh(@RequestBody RefreshJwtRequest refreshJwtRequest){
        return authService.refresh(refreshJwtRequest.getRefreshToken());
    }
}
