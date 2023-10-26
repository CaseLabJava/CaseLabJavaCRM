package com.greenatom.service.exceptions;

public class PasswordsNotMatchingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PasswordsNotMatchingException() {
        super("Passwords doesn't match!");
    }
}
