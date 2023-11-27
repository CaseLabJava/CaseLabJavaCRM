package com.greenatom.clientselfservice.security.service;

import com.greenatom.clientselfservice.domain.dto.security.AuthDTO;
import com.greenatom.clientselfservice.domain.dto.security.JwtResponse;
import com.greenatom.clientselfservice.domain.dto.security.ClientRegistrationDTO;
import com.greenatom.clientselfservice.domain.entity.Client;
import com.greenatom.clientselfservice.domain.mapper.ClientMapper;
import com.greenatom.clientselfservice.security.jwt.JwtCore;
import com.greenatom.clientselfservice.service.impl.ClientService;
import com.greenatom.clientselfservice.utils.exception.AuthException;
import io.jsonwebtoken.Claims;
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
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public JwtResponse registration(ClientRegistrationDTO registrationDTO) {
        Client client = clientMapper.toEntity(clientService.save(registrationDTO));
        String accessToken = jwtCore.generateAccessToken(client);
        String refreshToken = jwtCore.generateRefreshToken(client);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse login(AuthDTO authDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDto.getEmail(), authDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw AuthException.CODE.NO_SUCH_USERNAME_OR_PWD.get();
        }
        final Client client = clientService
                .findByEmail(authDto.getEmail())
                .orElseThrow(AuthException.CODE.NO_SUCH_USERNAME_OR_PWD::get);
        final String accessToken = jwtCore.generateAccessToken(client);
        final String refreshToken = jwtCore.generateRefreshToken(client);
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
            final String email = claims.getSubject();
            final Integer id = (Integer) claims.get("client_id");
            Client clientForJwt = new Client();
            clientForJwt.setEmail(email);
            clientForJwt.setId(Long.valueOf(id));
            final String accessToken = jwtCore.generateAccessToken(clientForJwt);
            if (action.equals("refresh")) {
                final String newRefreshToken = jwtCore.generateRefreshToken(clientForJwt);
                return new JwtResponse(accessToken, newRefreshToken);
            } else {
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

}
