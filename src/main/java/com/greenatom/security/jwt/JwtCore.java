package com.greenatom.security.jwt;

import com.greenatom.domain.entity.Employee;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtCore {

    private final SecretKey accessKey;

    private final SecretKey refreshKey;

    private final Logger logger = LoggerFactory.getLogger(JwtCore.class);

    @Autowired
    public JwtCore(@Value("${access_token}") String accessKey,
                   @Value("${refresh_token}")String refreshKey) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
    }

    public String generateAccessToken(Employee employee){
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(employee.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessExpiration)
                .claim("role", employee.getRole().getName())
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Employee employee){
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(employee.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessExpiration)
                .claim("role", employee.getRole().getName())
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String accessToken){
        return validateToken(accessToken,accessKey);
    }

    public boolean validateRefreshToken(String refreshToken){
        return validateToken(refreshToken,refreshKey);
    }

    public Claims extractAccessClaims(String accessToken){
        return extractClaims(accessToken,accessKey);
    }

    public Claims extractRefreshClaims(String refreshToken){
        return extractClaims(refreshToken,refreshKey);
    }

    private Claims extractClaims(String token,Key key){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean validateToken(String token, Key key){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.info("Token has expired");
        } catch (UnsupportedJwtException e) {
            logger.info("Something went wrong");
        } catch (MalformedJwtException e) {
            logger.info("Invalid JWT token");
        } catch (SignatureException e) {
            logger.info("Invalid signature");
        } catch (IllegalArgumentException e) {
            logger.info("");
        }
        return false;
    }

}
