package ru.covenant.code.landing.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxLengthValidator implements ConstraintValidator<MaxLength, String> {

    private int max;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.length() <= max;
    }

    @Override
    public void initialize(MaxLength constraintAnnotation) {
        this.max = constraintAnnotation.value();
    }
}
