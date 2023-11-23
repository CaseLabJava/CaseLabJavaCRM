package com.greenatom.controller.handler;

import com.greenatom.exception.PreparingOrderException;
import com.greenatom.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PreparingOrderExceptionHandler {

    @ExceptionHandler(PreparingOrderException.class)
    public ResponseEntity<ErrorMessage> handleOrderItemException(PreparingOrderException e) {
        PreparingOrderException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case INCORRECT_ROLE, NO_PERMISSION-> HttpStatus.FORBIDDEN;
            case CANNOT_FINISH_ORDER -> HttpStatus.CONFLICT;
            case NO_SUCH_PREPARING_ORDER -> HttpStatus.NOT_FOUND;
            case INVALID_STATUS -> null;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}
