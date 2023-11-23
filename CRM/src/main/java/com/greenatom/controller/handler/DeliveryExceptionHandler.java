package com.greenatom.controller.handler;

import com.greenatom.exception.DeliveryException;
import com.greenatom.exception.message.DeliveryErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DeliveryExceptionHandler {
    @ExceptionHandler(DeliveryException.class)
    public ResponseEntity<DeliveryErrorMessage> handleDeliveryException(DeliveryException e) {
        DeliveryException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_DELIVERY, NO_SUCH_COURIER -> HttpStatus.NOT_FOUND;
            case FORBIDDEN, INVALID_STATUS -> HttpStatus.FORBIDDEN;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new DeliveryErrorMessage(codeStr, e.getMessage()));
    }
}
