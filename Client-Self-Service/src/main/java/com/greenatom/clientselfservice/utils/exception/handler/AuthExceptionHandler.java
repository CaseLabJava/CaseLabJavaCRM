package com.greenatom.clientselfservice.utils.exception.handler;

import com.greenatom.clientselfservice.utils.exception.AuthException;
import com.greenatom.clientselfservice.utils.exception.message.AuthErrorMessage;
import com.greenatom.clientselfservice.utils.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<AuthErrorMessage> handleAuthException(AuthException e) {
        AuthException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_USERNAME_OR_PWD -> HttpStatus.NOT_FOUND;
            case JWT_VALIDATION_ERROR -> HttpStatus.UNAUTHORIZED;
            case EMAIL_IN_USE -> HttpStatus.CONFLICT;
            case INVALID_EMAIL -> HttpStatus.CONFLICT;
        };
        String codeStr = code.toString();
        return ResponseEntity
                .status(status)
                .body(new AuthErrorMessage(codeStr, e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(HttpStatus.BAD_REQUEST.name(), e.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorMessage> handleAuthException(HttpClientErrorException.Unauthorized e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(HttpStatus.UNAUTHORIZED.name(), e.getMessage()));
    }
}

