package com.greenatom.security.jwt;

import com.greenatom.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Класс JwtFilter является фильтром для обработки JWT-токенов. Он расширяет интерфейс OncePerRequestFilter и
 * использует компоненты JwtCore и UserDetailsServiceImpl для обработки входящих запросов.
 *
 * <p>В методе doFilterInternal фильтр обрабатывает входящий запрос, извлекает JWT-токен и проверяет его валидность.
 * Если токен валиден, фильтр устанавливает соответствующий аутентификационный объект в контексте безопасности
 * SecurityContextHolder для обеспечения доступа к защищенным ресурсам.
 *
 * <p>Метод extractJwt извлекает JWT из заголовка Authorization, проверяет его наличие и корректность, и возвращает
 * токен в случае успеха.
 * @autor Андрей Начевный
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtCore jwtCore;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = extractJwt(request);
        if(jwt != null && jwtCore.validateAccessToken(jwt) && SecurityContextHolder.getContext().getAuthentication() == null){
            Claims claims = jwtCore.extractAccessClaims(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()));
        }
        filterChain.doFilter(request,response);
    }

    private String extractJwt(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }
}
