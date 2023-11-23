package com.greenatom.controller.handler;

import com.greenatom.exception.ClientException;
import com.greenatom.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ClientExceptionHandler {
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorMessage> handleClientException(ClientException e) {
        ClientException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_CLIENT -> HttpStatus.NOT_FOUND;
            case INCORRECT_ATTRIBUTE_NAME -> HttpStatus.BAD_REQUEST;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}
