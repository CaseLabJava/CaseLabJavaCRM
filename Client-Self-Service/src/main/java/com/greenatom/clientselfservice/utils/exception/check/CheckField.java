package com.greenatom.clientselfservice.utils.exception.check;

import com.greenatom.clientselfservice.utils.exception.AuthException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class CheckField {

    public static void checkRegistration(BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorMsg.append("Error: ").append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";").append(" ");
            }
            throw AuthException.CODE.INVALID_EMAIL.get(errorMsg.toString());
        }
    }
}
