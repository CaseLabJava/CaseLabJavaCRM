package com.greenatom.exception;

import lombok.Getter;

@Getter
public class ProductException extends RuntimeException {
    @Getter
    public enum CODE {
        NO_SUCH_PRODUCT("No product with such id"),
        PRODUCT_IN_ORDER("Cannot delete product that in order"),
        INCORRECT_ATTRIBUTE_NAME("Incorrec attribute");
        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public ProductException get() {
            return new ProductException(this, this.codeDescription);
        }

        public ProductException get(String msg) {
            return new ProductException(this, this.codeDescription + " : " + msg);
        }

        public ProductException get(Throwable e) {
            return new ProductException(this, this.codeDescription + " : " + e.getMessage());
        }

        public ProductException get(Throwable e, String msg) {
            return new ProductException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final ProductException.CODE code;

    protected ProductException(ProductException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected ProductException(ProductException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}