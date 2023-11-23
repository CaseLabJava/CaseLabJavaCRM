package com.greenatom.exception;

import lombok.Getter;

@Getter
public class ClaimException extends RuntimeException {
    @Getter
    public enum CODE {
        NO_SUCH_CLAIM("No claim with such id"),
        INVALID_STATUS("Order status are invalid"),
        NO_PERMISSION("You don`t have permissions to do this"),
        CLAIM_PROCESSING("Claim already in process");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public ClaimException get() {
            return new ClaimException(this, this.codeDescription);
        }

        public ClaimException get(String msg) {
            return new ClaimException(this, this.codeDescription + " : " + msg);
        }

        public ClaimException get(Throwable e) {
            return new ClaimException(this,
                    this.codeDescription + " : " + e.getMessage());
        }

        public ClaimException get(Throwable e, String msg) {
            return new ClaimException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final ClaimException.CODE code;

    protected ClaimException(ClaimException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected ClaimException(ClaimException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
