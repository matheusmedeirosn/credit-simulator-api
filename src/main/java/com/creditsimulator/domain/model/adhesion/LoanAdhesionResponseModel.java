package com.creditsimulator.domain.model.adhesion;

import com.creditsimulator.domain.enums.PaymentMethod;
import com.creditsimulator.domain.model.LoanBase;
import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
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
