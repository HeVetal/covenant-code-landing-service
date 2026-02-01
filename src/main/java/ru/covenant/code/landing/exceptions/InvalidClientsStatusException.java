package ru.covenant.code.landing.exceptions;

public class InvalidClientsStatusException extends RuntimeException{
    private static final String MESSAGE = "Некорректный статус";

    public InvalidClientsStatusException(String s) {
        super(MESSAGE);
    }
}
