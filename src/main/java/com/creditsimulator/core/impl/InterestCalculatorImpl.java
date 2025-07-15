package com.creditsimulator.core.impl;

import com.creditsimulator.core.InterestCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class InterestCalculatorImpl implements InterestCalculator {

    private static final int MONTHS_IN_YEAR = 12;
    private static final int DECIMAL_SCALE = 10;
    private static final int FINAL_SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public BigDecimal calculateMonthlyRate(BigDecimal annualRate) {
        return annualRate.divide(
                BigDecimal.valueOf(MONTHS_IN_YEAR),
                DECIMAL_SCALE,
                ROUNDING_MODE
        );
    }

    @Override
    public BigDecimal calculateMonthlyPayment(
            BigDecimal loanAmount,
            BigDecimal monthlyRate,
            int termMonths) {

        // (1 + r)
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);

        // (1 + r)^-n
        BigDecimal compoundedRate = onePlusRate.pow(-termMonths, MathContext.DECIMAL128);

        // 1 - (1 + r)^-n
        BigDecimal denominator = BigDecimal.ONE.subtract(compoundedRate);

        // r / (1 - (1 + r)^-n)
        BigDecimal paymentFactor = monthlyRate.divide(denominator, DECIMAL_SCALE, ROUNDING_MODE);

        // PV × [r / (1 - (1 + r)^-n)]
        return loanAmount.multiply(paymentFactor)
                .setScale(FINAL_SCALE, ROUNDING_MODE);

    }

}
