package com.creditsimulator.core;

import java.math.BigDecimal;

public interface InterestCalculator {

    BigDecimal calculateMonthlyRate(BigDecimal annualRate);

    BigDecimal calculateMonthlyPayment(
            BigDecimal loanAmount,
            BigDecimal monthlyRate,
            int termMonths);

}
