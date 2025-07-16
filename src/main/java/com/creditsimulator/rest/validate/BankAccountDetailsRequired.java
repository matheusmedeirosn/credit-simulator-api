package com.creditsimulator.rest.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BankAccountDetailsRequiredValidator.class)
public @interface BankAccountDetailsRequired {
    String message() default "Detalhes bancários são obrigatórios para débito automático";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}