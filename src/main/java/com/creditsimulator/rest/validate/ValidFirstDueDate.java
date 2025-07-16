package com.creditsimulator.rest.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstDueDateValidator.class)
@Documented
public @interface ValidFirstDueDate {
    String message() default "A data do primeiro vencimento deve estar entre hoje e 5 meses no futuro";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int maxMonths() default 5;
}