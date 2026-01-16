package ru.covenant.code.landing.exceptions;

public class PersistenceException extends RuntimeException{
    private static final String MESSAGE = "Ошибка сохранения";

    public PersistenceException() {
        super(MESSAGE);
    }
}
