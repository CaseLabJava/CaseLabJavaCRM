package com.greenatom.controller.handler;

import com.greenatom.exception.FileException;
import com.greenatom.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FileExceptionHandler {
    @ExceptionHandler(FileException.class)
    public ResponseEntity<ErrorMessage> handleClientException(FileException e) {
        FileException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case MINIO, IO -> HttpStatus.INTERNAL_SERVER_ERROR;
            case INVALID_KEY -> HttpStatus.BAD_REQUEST;
            case ALGORITHM_NOT_FOUND -> HttpStatus.NOT_FOUND;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}