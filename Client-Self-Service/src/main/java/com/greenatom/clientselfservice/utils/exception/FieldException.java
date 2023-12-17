package com.greenatom.clientselfservice.utils.exception;

import lombok.Getter;

@Getter
public class FieldException extends RuntimeException{
    @Getter
    public enum CODE {
        FIELDS_LIMITATION("Some field has error");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public FieldException get() {
            return new FieldException(this, this.codeDescription);
        }

        public FieldException get(String msg) {
            return new FieldException(this, this.codeDescription + " : " + msg);
        }

        public FieldException get(Throwable e) {
            return new FieldException(this, this.codeDescription + " : " + e.getMessage());
        }

        public FieldException get(Throwable e, String msg) {
            return new FieldException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final FieldException.CODE code;

    protected FieldException(FieldException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected FieldException(FieldException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
