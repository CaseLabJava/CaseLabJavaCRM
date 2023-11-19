package com.greenatom.clientselfservice.security.jwt;

import com.greenatom.clientselfservice.domain.entity.Client;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JwtCore {

    private final SecretKey accessKey;
    private final SecretKey refreshKey;

    @Autowired
    public JwtCore(@Value("${client_access_token}") String accessKey,
                   @Value("${client_refresh_token}")String refreshKey) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
    }

    public String generateAccessToken(Client client){
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(client.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessExpiration)
                .claim("client_id", client.getId())
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Client client){
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(client.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessExpiration)
                .claim("client_id", client.getId())
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

    private Claims extractClaims(String token, Key key){
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
            log.info("Token has expired");
        } catch (UnsupportedJwtException e) {
            log.info("Something went wrong");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token");
        } catch (SignatureException e) {
            log.info("Invalid signature");
        } catch (IllegalArgumentException e) {
            log.info("");
        }
        return false;
    }

}
