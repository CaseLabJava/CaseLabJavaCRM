package com.greenatom.exception;

import lombok.Getter;

@Getter
public class CourierException extends RuntimeException{
    @Getter
    public enum CODE {
        NO_SUCH_COURIER("No courier with such id");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public CourierException get() {
            return new CourierException(this, this.codeDescription);
        }

        public CourierException get(String msg) {
            return new CourierException(this, this.codeDescription + " : " + msg);
        }

        public CourierException get(Throwable e) {
            return new CourierException(this, this.codeDescription + " : " + e.getMessage());
        }

        public CourierException get(Throwable e, String msg) {
            return new CourierException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final CourierException.CODE code;

    protected CourierException(CourierException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected CourierException(CourierException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
