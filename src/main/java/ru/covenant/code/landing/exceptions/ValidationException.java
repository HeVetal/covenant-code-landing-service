package ru.covenant.code.landing.exceptions;

import ru.covenant.code.landing.exceptions.enumerated.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends ParentException {
    private final List<FieldError> fieldErrors = new ArrayList<>();

    public ValidationException(String message) {
        super(message, ErrorCode.VALIDATION_ERROR);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, ErrorCode.VALIDATION_ERROR, cause);
    }

    public ValidationException(String message, List<FieldError> fieldErrors) {
        super(message, ErrorCode.VALIDATION_ERROR);
        if (fieldErrors != null) {
            this.fieldErrors.addAll(fieldErrors);
        }
    }

    public ValidationException(String field, String message) {
        super(message, ErrorCode.VALIDATION_ERROR);
        this.fieldErrors.add(new FieldError(field, message));
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }

    public static class FieldError {
        private final String field;
        private final String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}