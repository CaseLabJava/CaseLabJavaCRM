package com.greenatom.controller.handler;

import com.greenatom.exception.message.ErrorMessage;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ValidationExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleOrderItemException(ConstraintViolationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String codeStr = "INCORRECT_INPUT";
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getConstraintViolations().toString()));
    }
}
