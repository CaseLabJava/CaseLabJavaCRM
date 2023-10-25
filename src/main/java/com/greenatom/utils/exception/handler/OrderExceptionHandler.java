package com.greenatom.utils.exception.handler;

import com.greenatom.utils.exception.OrderException;
import com.greenatom.utils.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorMessage> handleOrderException(OrderException e) {
        OrderException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_CLIENT, NO_SUCH_EMPLOYEE, NO_SUCH_ORDER -> HttpStatus.NOT_FOUND;
            case CANNOT_DELETE_ORDER -> HttpStatus.CONFLICT;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}
