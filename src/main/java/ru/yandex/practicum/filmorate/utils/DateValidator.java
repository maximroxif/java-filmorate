package ru.yandex.practicum.filmorate.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<DateAnnatation, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate != null) {
            return localDate.isAfter(LocalDate.of(1985, 12, 28));
        }
        return true;
    }
}
