package ru.greenatom.acquiringservice.exception;

import lombok.Getter;

@Getter
public class AcquiringException extends RuntimeException{
    @Getter
    public enum CODE {
        BANK_ACCOUNT_NOT_FOUND("Bank account not found"),
        NOT_ENOUGH_BALANCE("Not enough money on balance");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public AcquiringException get() {
            return new AcquiringException(this, this.codeDescription);
        }

        public AcquiringException get(String msg) {
            return new AcquiringException(this, this.codeDescription + " : " + msg);
        }

        public AcquiringException get(Throwable e) {
            return new AcquiringException(this, this.codeDescription + " : " + e.getMessage());
        }

        public AcquiringException get(Throwable e, String msg) {
            return new AcquiringException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final AcquiringException.CODE code;

    protected AcquiringException(AcquiringException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected AcquiringException(AcquiringException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
