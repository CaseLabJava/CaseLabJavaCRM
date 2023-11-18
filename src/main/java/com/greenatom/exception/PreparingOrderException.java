package com.greenatom.exception;

import lombok.Getter;

@Getter
public class PreparingOrderException extends RuntimeException {
    @Getter
    public enum CODE {
        INCORRECT_ROLE("Only warehouse employee can prepare order"),
        NO_PERMISSION("You don`t have permissions to do this"),
        NO_SUCH_PREPARING_ORDER("No preparing order with such id"),
        CANNOT_FINISH_ORDER("Only preparing orders with status IN_PROCESS can be finished"),
        INVALID_STATUS("Invalid status of preparing order");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public PreparingOrderException get() {
            return new PreparingOrderException(this, this.codeDescription);
        }

        public PreparingOrderException get(String msg) {
            return new PreparingOrderException(this, this.codeDescription + " : " + msg);
        }

        public PreparingOrderException get(Throwable e) {
            return new PreparingOrderException(this, this.codeDescription + " : " + e.getMessage());
        }

        public PreparingOrderException get(Throwable e, String msg) {
            return new PreparingOrderException(this, e, this.codeDescription + " : " + msg);
        }

    }

    protected final PreparingOrderException.CODE code;

    protected PreparingOrderException(PreparingOrderException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected PreparingOrderException(PreparingOrderException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}