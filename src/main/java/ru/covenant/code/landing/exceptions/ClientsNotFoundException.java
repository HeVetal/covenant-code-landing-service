package ru.covenant.code.landing.exceptions;

public class ClientsNotFoundException extends RuntimeException{
    private static final String MESSAGE = "Заявка не найдена";

    public ClientsNotFoundException(String s) {
        super(MESSAGE);
    }
}
