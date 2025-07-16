package com.creditsimulator.domain.model.adhesion;

import com.creditsimulator.domain.enums.PaymentMethod;
import com.creditsimulator.domain.model.LoanBase;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class LoanAdhesionResponseModel extends LoanBase {

    private String simulationId;
    private LocalDate contractDate;
    private LocalDate firstDueDate;
    private BigDecimal monthlyPayment;
    private PaymentMethod paymentMethod;
    private BigDecimal totalInterest;
    private BigDecimal totalAmount;
    private BigDecimal annualInterestRate;
    private Integer months;
    private BigDecimal amount;
}
