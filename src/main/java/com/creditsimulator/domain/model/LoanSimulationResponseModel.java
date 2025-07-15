package com.creditsimulator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoanSimulationResponseModel extends LoanBase {

    private BigDecimal monthlyPayment;
    private BigDecimal totalInterest;
    private BigDecimal totalAmount;
    private BigDecimal annualInterestRate;

    public LoanSimulationResponseModel(
            BigDecimal amount,
            Integer months,
            BigDecimal monthlyPayment,
            BigDecimal totalInterest,
            BigDecimal totalAmount,
            BigDecimal annualInterestRate
    ) {
        super(amount, months);
        this.monthlyPayment = monthlyPayment;
        this.totalInterest = totalInterest;
        this.totalAmount = totalAmount;
        this.annualInterestRate = annualInterestRate;
    }

}
