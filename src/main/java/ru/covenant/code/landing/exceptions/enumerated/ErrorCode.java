package ru.covenant.code.landing.exceptions.enumerated;

public enum ErrorCode {
    NOT_FOUND("Ошибка отсутствующего ресурса"),
    VALIDATION_ERROR("Ошибка валидации"),
    BUSINESS_ERROR("Ошибка бизнес-логики"),
    COMMON_ERROR("Общая ошибка");

    private final String defaultMessage;

    ErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
