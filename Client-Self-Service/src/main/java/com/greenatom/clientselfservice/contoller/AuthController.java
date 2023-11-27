package com.greenatom.clientselfservice.contoller;

import com.greenatom.clientselfservice.contoller.api.AuthApi;
import com.greenatom.clientselfservice.domain.dto.security.AuthDTO;
import com.greenatom.clientselfservice.domain.dto.security.ClientRegistrationDTO;
import com.greenatom.clientselfservice.domain.dto.security.JwtResponse;
import com.greenatom.clientselfservice.domain.dto.security.RefreshJwtRequest;
import com.greenatom.clientselfservice.security.service.AuthService;
import com.greenatom.clientselfservice.utils.exception.check.CheckField;
import com.greenatom.clientselfservice.utils.exception.validation.ClientRegistrationValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("self_service/auth")
public class AuthController implements AuthApi {

    private final ClientRegistrationValidator clientRegistrationValidator;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> registration(
            @RequestBody @Valid ClientRegistrationDTO clientRegistrationDTO,
            BindingResult bindingResult) {
            clientRegistrationValidator.validate(clientRegistrationDTO,bindingResult);
            CheckField.checkRegistration(bindingResult);
            JwtResponse jwt = authService.registration(clientRegistrationDTO);

            return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthDTO authDTO) {
        JwtResponse jwt = authService.login(authDTO);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/access_token")
    public  ResponseEntity<JwtResponse> getAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return ResponseEntity.ok(authService.getAccessToken(refreshJwtRequest.getRefreshToken()));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return ResponseEntity.ok(authService.refresh(refreshJwtRequest.getRefreshToken()));
    }
}
