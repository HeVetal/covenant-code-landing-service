package ru.covenant.code.landing.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.covenant.code.landing.validation.annotation.ValidPhone;

public class ValidPhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private String regexPattern;

    @Override
    public void initialize(ValidPhone annotation) {
        regexPattern = annotation.pattern().isEmpty()
                ? "^(\\+7|8)?\\s?\\(?\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{2}[\\s-]?\\d{2}$" :
                annotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return true;

        String phone = value.trim();
        if(phone.isEmpty()) return true;

        return phone.matches(regexPattern);
    }


}
