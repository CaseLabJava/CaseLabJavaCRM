package com.greenatom.controller.handler;

import com.greenatom.exception.OrderItemException;
import com.greenatom.exception.message.OrderItemErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderItemExceptionHandler {

    @ExceptionHandler(OrderItemException.class)
    public ResponseEntity<OrderItemErrorMessage> handleOrderItemException(OrderItemException e) {
        OrderItemException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_PRODUCT, NO_SUCH_ORDER, NO_SUCH_ORDER_ITEM -> HttpStatus.NOT_FOUND;
            case INCORRECT_ATTRIBUTE_NAME -> HttpStatus.BAD_REQUEST;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new OrderItemErrorMessage(codeStr, e.getMessage()));
    }
}