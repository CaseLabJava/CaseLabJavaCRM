package com.greenatom.controller.handler;

import com.greenatom.exception.AuthException;
import com.greenatom.exception.message.AuthErrorMessage;
import com.greenatom.exception.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Этот код представляет собой обработчик исключений в рамках REST-контроллера. Он содержит три метода для обработки
 * различных исключений, которые могут возникнуть при выполнении операций авторизации и поиска сущностей.
 *
 * <p>Метод handleEntityNotFoundException используется для обработки исключения EntityNotFoundException и возвращает
 * HTTP-ответ с кодом 404 и сообщением "Entity Not Found" в случае, если сообщение об ошибке в исключении не определено.
 *
 * <p>Метод handleEmailAlreadyUsedException используется для обработки исключения EmailAlreadyUsedException и возвращает
 * HTTP-ответ с кодом 401 и сообщением "Authorization error: Email is already in use!" в случае, если сообщение об
 * ошибке в исключении не определено.
 *
 * <p>Метод handleUsernameAlreadyUsedException используется для обработки исключения UsernameAlreadyUsedException и
 * возвращает HTTP-ответ с кодом 401 и сообщением "Authorization error: Login name already used!" в случае, если
 * сообщение об ошибке в исключении не определено.
 *
 * <p>Этот код предназначен для обработки исключений, которые могут возникнуть при выполнении операций авторизации и
 * поиска сущностей в REST-сервисе.
 * @author Змаев Даниил, Максим Быков
 * @version 1.0
 */

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<AuthErrorMessage> handleAuthException(AuthException e) {
        AuthException.CODE code = e.getCode();
        HttpStatus status = switch (code) {
            case NO_SUCH_USERNAME_OR_PWD -> HttpStatus.NOT_FOUND;
            case JWT_VALIDATION_ERROR -> HttpStatus.UNAUTHORIZED;
            case INVALID_REPEAT_PASSWORD -> HttpStatus.BAD_REQUEST;
            case EMAIL_IN_USE -> HttpStatus.CONFLICT;
        };
        String codeStr = code.toString();
        return ResponseEntity
                .status(status)
                .body(new AuthErrorMessage(codeStr, e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(HttpStatus.BAD_REQUEST.name(), e.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorMessage> handleAuthException(HttpClientErrorException.Unauthorized e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(HttpStatus.UNAUTHORIZED.name(), e.getMessage()));
    }
}
