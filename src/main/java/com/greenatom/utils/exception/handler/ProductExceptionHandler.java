package com.greenatom.utils.exception.handler;

import com.greenatom.utils.exception.ProductException;
import com.greenatom.utils.exception.message.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ProductExceptionHandler {
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorMessage> handleOrderItemException(ProductException e) {
        ProductException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_PRODUCT -> HttpStatus.NOT_FOUND;
        };
        String codeStr = code.toString();
        return ResponseEntity
                .status(status)
                .body(new ErrorMessage(codeStr, e.getMessage()));
    }
}
