package ru.covenant.code.landing.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.covenant.code.landing.validation.ValidPhoneValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidPhoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {
    String message() default "Некорректный номер телефона";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern() default "";



}
