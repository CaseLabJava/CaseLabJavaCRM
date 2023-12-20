package com.greenatom.clientselfservice.utils.exception.handler;

import com.greenatom.clientselfservice.utils.exception.FieldException;
import com.greenatom.clientselfservice.utils.exception.message.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FieldExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FieldException.class)
    public ResponseEntity<ErrorMessage> handleRegistrationException(FieldException exception) {
        FieldException.CODE code = exception.getCode();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String codeStr = code.toString();
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, exception.getMessage()));
    }
}
