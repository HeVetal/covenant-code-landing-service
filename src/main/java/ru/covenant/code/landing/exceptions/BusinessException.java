package ru.covenant.code.landing.exceptions;

import ru.covenant.code.landing.exceptions.enumerated.ErrorCode;

public class BusinessException extends ParentException {

    public BusinessException(String message) {
        super(message, ErrorCode.BUSINESS_ERROR);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, ErrorCode.BUSINESS_ERROR, cause);
    }

    public BusinessException(String message, String businessCode) {
        super(String.format("[%s] %s", businessCode, message),
                ErrorCode.BUSINESS_ERROR);
    }
}