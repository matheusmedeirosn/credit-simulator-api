package com.creditsimulator.domain.model.simulation;

import com.creditsimulator.domain.model.LoanBase;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoanSimulationResponseModel extends LoanBase {

    private BigDecimal monthlyPayment;
    private BigDecimal totalInterest;
    private BigDecimal totalAmount;
    private BigDecimal annualInterestRate;
    private String e2e;

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
        this.e2e = UUID.randomUUID().toString();
    }

}
