package com.greenatom.PaymentService.exception;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException{
    @Getter
    public enum CODE {
        NO_SUCH_PAYMENT("No payment with such id"),
        NO_SUCH_CARD("No card with such id");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public PaymentException get() {
            return new PaymentException(this, this.codeDescription);
        }

        public PaymentException get(String msg) {
            return new PaymentException(this, this.codeDescription + " : " + msg);
        }

        public PaymentException get(Throwable e) {
            return new PaymentException(this, this.codeDescription + " : " + e.getMessage());
        }

        public PaymentException get(Throwable e, String msg) {
            return new PaymentException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final PaymentException.CODE code;

    protected PaymentException(PaymentException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected PaymentException(PaymentException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
