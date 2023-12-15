package com.greenatom.exception;

import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {
    @Getter
    public enum CODE {
        NO_SUCH_REVIEW("No review with such id"),
        INCORRECT_ATTRIBUTE_NAME("Incorrect attribute name");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public ReviewException get() {
            return new ReviewException(this, this.codeDescription);
        }

        public ReviewException get(String msg) {
            return new ReviewException(this, this.codeDescription + " : " + msg);
        }

        public ReviewException get(Throwable e) {
            return new ReviewException(this, this.codeDescription + " : " + e.getMessage());
        }

        public ReviewException get(Throwable e, String msg) {
            return new ReviewException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final ReviewException.CODE code;

    protected ReviewException(ReviewException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected ReviewException(ReviewException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
