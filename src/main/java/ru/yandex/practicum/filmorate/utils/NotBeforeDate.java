package ru.yandex.practicum.filmorate.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface NotBeforeDate {

    String message() default "Дата не раньше 1895-12-28";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
