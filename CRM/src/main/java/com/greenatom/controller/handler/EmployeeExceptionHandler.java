package com.greenatom.controller.handler;

import com.greenatom.exception.EmployeeException;
import com.greenatom.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class EmployeeExceptionHandler {
    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<ErrorMessage> handleClientException(EmployeeException e) {
        EmployeeException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_EMPLOYEE -> HttpStatus.NOT_FOUND;
            case INCORRECT_JOB_POSITION, INCORRECT_ATTRIBUTE_NAME -> HttpStatus.BAD_REQUEST;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}
