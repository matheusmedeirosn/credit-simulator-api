package com.creditsimulator.domain.model.adhesion;

import com.creditsimulator.domain.enums.PaymentMethod;
import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanAdhesionRequestModel {

    private String simulationId;
    private String customerId;
    private LocalDate firstDueDate;
    private PaymentMethod paymentMethod;
    private BankAccountDetailsModel bankAccountDetails;
    private BigDecimal monthlyPayment;
    private BigDecimal totalInterest;
    private BigDecimal totalAmount;
    private BigDecimal annualInterestRate;

    public void fromSimulation(LoanSimulationResponseModel simulation){
        this.monthlyPayment = simulation.getMonthlyPayment();
        this.totalInterest = simulation.getTotalInterest();
        this.totalAmount = simulation.getTotalAmount();
        this.annualInterestRate = simulation.getAnnualInterestRate();
    }

}
