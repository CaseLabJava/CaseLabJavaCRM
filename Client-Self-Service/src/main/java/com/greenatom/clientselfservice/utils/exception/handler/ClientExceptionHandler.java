package com.greenatom.clientselfservice.utils.exception.handler;

import com.greenatom.clientselfservice.utils.exception.ClientException;
import com.greenatom.clientselfservice.utils.exception.message.ErrorMessage;
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
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}
