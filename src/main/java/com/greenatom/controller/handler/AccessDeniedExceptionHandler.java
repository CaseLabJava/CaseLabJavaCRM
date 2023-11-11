package com.greenatom.controller.handler;

import com.greenatom.exception.message.ErrorMessage;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(AccessDeniedException ex) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String message = ex.getMessage() + " to employee with username: %s"
                .formatted(username);

        return ResponseEntity
                .status(FORBIDDEN)
                .body(new ErrorMessage(FORBIDDEN.name(), message));
    }
}
