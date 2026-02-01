package ru.covenant.code.landing.exceptions;

import ru.covenant.code.landing.exceptions.enumerated.ErrorCode;

public abstract class ParentException extends RuntimeException {
    private final ErrorCode errorCode;

    public ParentException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ParentException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
