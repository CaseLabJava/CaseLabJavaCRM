package com.greenatom.controller.handler;

import com.greenatom.exception.CourierException;
import com.greenatom.exception.message.CourierErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CourierExceptionHandler {
    @ExceptionHandler(CourierException.class)
    public ResponseEntity<CourierErrorMessage> handleCourierException(CourierException e) {
        CourierException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_COURIER -> HttpStatus.NOT_FOUND;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new CourierErrorMessage(codeStr, e.getMessage()));
    }
}
