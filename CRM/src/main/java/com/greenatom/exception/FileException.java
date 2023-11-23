package com.greenatom.exception;

import lombok.Getter;

@Getter
public class FileException extends RuntimeException {

    @Getter
    public enum CODE {
        MINIO("Problem with minio"),
        INVALID_KEY("Invalid key"),
        ALGORITHM_NOT_FOUND("No such algorithm"),
        IO("Problem with IO");

        final String codeDescription;

        CODE(String codeDescription) {
            this.codeDescription = codeDescription;
        }

        public FileException get() {
            return new FileException(this, this.codeDescription);
        }

        public FileException get(String msg) {
            return new FileException(this, this.codeDescription + " : " + msg);
        }

        public FileException get(Throwable e) {
            return new FileException(this, this.codeDescription + " : " + e.getMessage());
        }

        public FileException get(Throwable e, String msg) {
            return new FileException(this, e, this.codeDescription + " : " + msg);
        }

    }

    protected final FileException.CODE code;

    protected FileException(FileException.CODE code, String msg) {
        this(code, null, msg);
    }

    protected FileException(FileException.CODE code, Throwable e, String msg) {
        super(msg, e);
        this.code = code;
    }
}
