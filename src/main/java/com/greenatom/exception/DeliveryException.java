package com.greenatom.exception;

import lombok.Getter;

@Getter
public class DeliveryException extends RuntimeException{
    @Getter
    public enum CODE {
        NO_SUCH_DELIVERY("No delivery with such id"),
        NO_SUCH_COURIER("No courier with such id"),
        FORBIDDEN("You don`t have access to do this"),
        INVALID_STATUS("Delivery status is invalid");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public DeliveryException get() {
            return new DeliveryException(this, this.codeDescription);
        }

        public DeliveryException get(String msg) {
            return new DeliveryException(this, this.codeDescription + " : " + msg);
        }

        public DeliveryException get(Throwable e) {
            return new DeliveryException(this, this.codeDescription + " : " + e.getMessage());
        }

        public DeliveryException get(Throwable e, String msg) {
            return new DeliveryException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final DeliveryException.CODE code;

    protected DeliveryException(DeliveryException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected DeliveryException(DeliveryException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
