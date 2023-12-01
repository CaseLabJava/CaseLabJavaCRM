package com.greenatom.exception;

import lombok.Getter;

@Getter
public class EmployeeException extends RuntimeException {
    @Getter
    public enum CODE {
        NO_SUCH_EMPLOYEE("No employee with username:"),
        INCORRECT_JOB_POSITION("There is no such Job Position"),
        INCORRECT_ATTRIBUTE_NAME("Incorrect attribute name");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public EmployeeException get() {
            return new EmployeeException(this, this.codeDescription);
        }

        public EmployeeException get(String msg) {
            return new EmployeeException(this, this.codeDescription + " : " + msg);
        }

        public EmployeeException get(Throwable e) {
            return new EmployeeException(this, this.codeDescription + " : " + e.getMessage());
        }

        public EmployeeException get(Throwable e, String msg) {
            return new EmployeeException(this, e, this.codeDescription + " : " + msg);
        }
    }

    protected final EmployeeException.CODE code;

    protected EmployeeException(EmployeeException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected EmployeeException(EmployeeException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
