package com.greenatom.exception;

import lombok.Getter;

@Getter
public class OrderException extends RuntimeException {

    @Getter
    public enum CODE {
        NO_SUCH_CLIENT("No person with such id"),
        NO_SUCH_EMPLOYEE("No employee with username:"),
        NO_SUCH_ORDER("No order with such id"),
        NO_SUCH_PRODUCT("No product with such id"),
        CANNOT_ASSIGN_ORDER("Only order with DRAFT status can be assigned"),
        CANNOT_DELETE_ORDER("Assigned order can not be deleted"),
        INVALID_ORDER("Order content are invalid"),
        INVALID_STATUS("Order status are invalid"),
        INCORRECT_DELIVERY_TYPE("Delivery type are invalid"),
        INCORRECT_ATTRIBUTE_NAME("Incorrect attribute name"),
        NOT_PERMIT("You don`t have permission to do this");

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

    protected final CODE code;

    protected OrderException(CODE code, String msg) {
        this(code, null, msg);
    }

    protected OrderException(CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}