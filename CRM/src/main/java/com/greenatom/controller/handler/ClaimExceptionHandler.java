package com.greenatom.controller.handler;

import com.greenatom.exception.ClaimException;
import com.greenatom.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ClaimExceptionHandler {
    @ExceptionHandler(ClaimException.class)
    public ResponseEntity<ErrorMessage> handleClientException(ClaimException e) {
        ClaimException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_CLAIM -> HttpStatus.NOT_FOUND;
            case INVALID_STATUS, NO_PERMISSION, CLAIM_PROCESSING -> HttpStatus.BAD_REQUEST;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}
