package ru.covenant.code.landing.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.covenant.code.landing.validation.annotation.ValidEmail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private Set<String> allowedTlds;

    @Override
    public void initialize(ValidEmail annotation) {
        allowedTlds = new HashSet<>(Arrays.asList(annotation.allowedTlds()));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) return true;

        String email = value.trim();
        if (email.isEmpty()) return true;

        int at = email.indexOf('@');
        if (at <= 0 || at != email.lastIndexOf('@')) return false;

        int lastDot = email.lastIndexOf('.');
        if (lastDot < at + 2 || lastDot == email.length() - 1) return false;

        String tld = email.substring(lastDot + 1).toLowerCase();
        return allowedTlds.contains(tld);
    }
}
