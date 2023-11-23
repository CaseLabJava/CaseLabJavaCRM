package com.greenatom.exception;

import lombok.Getter;

@Getter
public class OrderItemException extends RuntimeException {
    @Getter
    public enum CODE {
        NO_SUCH_PRODUCT("No product with such id"),
        NO_SUCH_ORDER("No order with such id"),
        INCORRECT_ATTRIBUTE_NAME("Incorrect attribute name"),
        NO_SUCH_ORDER_ITEM("No order item with such id");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public OrderItemException get() {
            return new OrderItemException(this, this.codeDescription);
        }

        public OrderItemException get(String msg) {
            return new OrderItemException(this, this.codeDescription + " : " + msg);
        }

        public OrderItemException get(Throwable e) {
            return new OrderItemException(this, this.codeDescription + " : " + e.getMessage());
        }

        public OrderItemException get(Throwable e, String msg) {
            return new OrderItemException(this, e, this.codeDescription + " : " + msg);
        }

    }

    protected final OrderItemException.CODE code;

    protected OrderItemException(OrderItemException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected OrderItemException(OrderItemException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }

}
