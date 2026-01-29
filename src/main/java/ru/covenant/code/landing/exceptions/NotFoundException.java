package ru.covenant.code.landing.exceptions;

import ru.covenant.code.landing.exceptions.enumerated.ErrorCode;

public class NotFoundException extends ParentException {

    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, ErrorCode.NOT_FOUND, cause);
    }

    public NotFoundException(String resourceName, Long id) {
        super(String.format("%s с id=%d не найден", resourceName, id),
                ErrorCode.NOT_FOUND);
    }

    public NotFoundException(String resourceName, String identifier) {
        super(String.format("%s '%s' не найден", resourceName, identifier),
                ErrorCode.NOT_FOUND);
    }
}