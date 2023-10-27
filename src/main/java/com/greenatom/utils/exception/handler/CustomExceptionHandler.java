package com.greenatom.utils.exception.handler;

import com.greenatom.domain.dto.ResponseDTO;
import com.greenatom.utils.exception.AuthException;
import com.greenatom.utils.exception.EmailAlreadyUsedException;
import com.greenatom.utils.exception.UsernameAlreadyUsedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
 * @autor Максим Быков
 * @version 1.0
 */

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ResponseDTO> handleEntityNotFoundException(EntityNotFoundException notFoundException) {

        ResponseDTO response = new ResponseDTO();
        if (notFoundException.getMessage() != null) response.setMessage(notFoundException.getMessage());
        else response.setMessage("Entity Not Found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    protected ResponseEntity<ResponseDTO> handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        ResponseDTO response = new ResponseDTO();
        if (e.getMessage() != null) response.setMessage(e.getMessage());
        else response.setMessage("Authorization error: Email is already in use!");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameAlreadyUsedException.class)
    protected ResponseEntity<ResponseDTO> handleUsernameAlreadyUsedException(
            UsernameAlreadyUsedException e) {
        ResponseDTO response = new ResponseDTO();
        if (e.getMessage() != null) response.setMessage(e.getMessage());
        else response.setMessage("Authorization error: Login name already used!");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<ResponseDTO> handleAuthException(AuthException authException){
        ResponseDTO response = new ResponseDTO();
        if (authException.getMessage() != null) response.setMessage(authException.getMessage());
        else response.setMessage("Authorization error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}