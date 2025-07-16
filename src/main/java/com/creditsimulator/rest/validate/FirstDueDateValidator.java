package com.creditsimulator.rest.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.ZoneId;

public class FirstDueDateValidator implements ConstraintValidator<ValidFirstDueDate, LocalDate> {

    private int maxMonths;
    private ZoneId saoPauloZone = ZoneId.of("America/Sao_Paulo");

    @Override
    public void initialize(ValidFirstDueDate constraintAnnotation) {
        this.maxMonths = constraintAnnotation.maxMonths();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Deixe @NotNull lidar com isso
        }

        LocalDate today = LocalDate.now(saoPauloZone);
        LocalDate maxDueDate = today.plusMonths(maxMonths);

        return !value.isBefore(today) && !value.isAfter(maxDueDate);
    }
}