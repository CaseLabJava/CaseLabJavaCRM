package com.greenatom.controller.handler;

import com.greenatom.exception.ProductException;
import com.greenatom.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ProductExceptionHandler {
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorMessage> handleOrderItemException(ProductException e) {
        ProductException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_PRODUCT -> HttpStatus.NOT_FOUND;
            case PRODUCT_IN_ORDER, INCORRECT_ATTRIBUTE_NAME -> HttpStatus.BAD_REQUEST;
        };
        String codeStr = code.toString();
        log.error(codeStr, e);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}
