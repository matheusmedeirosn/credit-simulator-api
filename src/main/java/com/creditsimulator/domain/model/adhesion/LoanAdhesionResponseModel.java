package com.creditsimulator.domain.model.adhesion;

import com.creditsimulator.domain.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LoanAdhesionResponseModel {

    private UUID contractId;
    private LocalDate contractDate;
    private LocalDate firstDueDate;
    private BigDecimal installmentAmount;
    private PaymentMethod paymentMethod;

}
