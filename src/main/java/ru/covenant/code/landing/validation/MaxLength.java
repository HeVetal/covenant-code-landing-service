package ru.covenant.code.landing.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxLengthValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLength {

    int value();

    String message() default "Превышена допустимая длина поля";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
