package com.greenatom.controller.handler;

import com.greenatom.exception.OrderException;
import com.greenatom.exception.message.OrderErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<OrderErrorMessage> handleOrderException(OrderException e) {
        OrderException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_CLIENT, NO_SUCH_EMPLOYEE, NO_SUCH_ORDER, NO_SUCH_PRODUCT -> HttpStatus.NOT_FOUND;
            case CANNOT_DELETE_ORDER, CANNOT_ASSIGN_ORDER, INVALID_ORDER,
                    INVALID_STATUS, INCORRECT_ATTRIBUTE_NAME -> HttpStatus.BAD_REQUEST;
            case NOT_PERMIT -> HttpStatus.FORBIDDEN;
            default -> null;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new OrderErrorMessage(codeStr, e.getMessage()));
    }
}
