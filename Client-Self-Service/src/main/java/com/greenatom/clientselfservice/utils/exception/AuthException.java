package com.greenatom.clientselfservice.utils.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{
    @Getter
    public enum CODE {
        NO_SUCH_USERNAME_OR_PWD("Incorrect username or password"),
        JWT_VALIDATION_ERROR("JWT validation error"),
        EMAIL_IN_USE("This email already used"),
        INVALID_EMAIL("This email is invalid");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public AuthException get() {
            return new AuthException(this, this.codeDescription);
        }

        public AuthException get(String msg) {
            return new AuthException(this, this.codeDescription + " : " + msg);
        }

        public AuthException get(Throwable e) {
            return new AuthException(this, this.codeDescription + " : " + e.getMessage());
        }

        public AuthException get(Throwable e, String msg) {
            return new AuthException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final AuthException.CODE code;

    protected AuthException(AuthException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected AuthException(AuthException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
