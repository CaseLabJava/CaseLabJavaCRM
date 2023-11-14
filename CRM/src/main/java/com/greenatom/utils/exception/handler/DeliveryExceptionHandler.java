package com.greenatom.utils.exception.handler;

import com.greenatom.utils.exception.DeliveryException;
import com.greenatom.utils.exception.message.DeliveryErrorMessage;
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
            case NO_SUCH_DELIVERY, INVALID_STATUS -> HttpStatus.NOT_FOUND;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new DeliveryErrorMessage(codeStr, e.getMessage()));
    }
}
