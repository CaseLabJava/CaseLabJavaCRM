package com.greenatom.utils.exception.handler;

import com.greenatom.utils.exception.OrderException;
import com.greenatom.utils.exception.message.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorMessage> handleOrderException(OrderException e) {
        OrderException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_CLIENT, NO_SUCH_EMPLOYEE -> HttpStatus.NOT_FOUND;
        };
        String codeStr = code.toString();
        // TODO: logging
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}
