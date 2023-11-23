package com.greenatom.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
    @Getter
    public enum CODE {
        NO_SUCH_CLIENT("No client with such id"),
        INCORRECT_ATTRIBUTE_NAME("Incorrect attribute name");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public ClientException get() {
            return new ClientException(this, this.codeDescription);
        }

        public ClientException get(String msg) {
            return new ClientException(this, this.codeDescription + " : " + msg);
        }

        public ClientException get(Throwable e) {
            return new ClientException(this, this.codeDescription + " : " + e.getMessage());
        }

        public ClientException get(Throwable e, String msg) {
            return new ClientException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final ClientException.CODE code;

    protected ClientException(ClientException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected ClientException(ClientException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
