package com.greenatom.utils.exception;

import lombok.Getter;

@Getter
public class OrderException extends RuntimeException {

    @Getter
    public enum CODE {
        NO_SUCH_CLIENT("No person with such id"),
        NO_SUCH_EMPLOYEE("No employee with such id");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public OrderException get() {
            return new OrderException(this, this.codeDescription);
        }

        public OrderException get(String msg) {
            return new OrderException(this, this.codeDescription + " : " + msg);
        }

        public OrderException get(Throwable e) {
            return new OrderException(this, this.codeDescription + " : " + e.getMessage());
        }

        public OrderException get(Throwable e, String msg) {
            return new OrderException(this, e, this.codeDescription + " : " + msg);
        }

    }

    protected CODE code;

    protected OrderException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected OrderException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

}
