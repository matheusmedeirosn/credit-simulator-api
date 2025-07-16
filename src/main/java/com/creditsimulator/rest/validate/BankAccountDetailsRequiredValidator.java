package com.creditsimulator.rest.validate;

import com.creditsimulator.domain.enums.PaymentMethod;
import com.creditsimulator.rest.message.request.BankAccountDetailsDTO;
import com.creditsimulator.rest.message.request.LoanAdhesionRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class BankAccountDetailsRequiredValidator implements ConstraintValidator<BankAccountDetailsRequired, LoanAdhesionRequestDTO> {

    @Autowired
    private Validator validator;

    @Override
    public boolean isValid(LoanAdhesionRequestDTO request, ConstraintValidatorContext context) {
        if (!PaymentMethod.DEBITO_AUTOMATICO.name().equals(request.paymentMethod())) return true;

        if (request.bankAccountDetails() == null) {
            addError(context, "bankAccountDetails", "Detalhes bancários são obrigatórios");
            return false;
        }

        Set<ConstraintViolation<BankAccountDetailsDTO>> violations =
                validator.validate(request.bankAccountDetails());

        if (!violations.isEmpty()) {
            for (ConstraintViolation<BankAccountDetailsDTO> violation : violations) {
                addError(context,
                        "bankAccountDetails." + violation.getPropertyPath(),
                        violation.getMessage());
            }
            return false;
        }

        return true;
    }

    private void addError(ConstraintValidatorContext context, String path, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(path)
                .addConstraintViolation();
    }
}