package com.creditSimulator.rest.controller;

import com.creditsimulator.core.impl.InterestCalculatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InterestCalculatorImplTest {

    private final InterestCalculatorImpl calculator = new InterestCalculatorImpl();

    @Test
    void calculateMonthlyRate_shouldReturnCorrectValueForStandardRate() {
        BigDecimal annualRate = new BigDecimal("0.12"); // 12% ao ano
        BigDecimal result = calculator.calculateMonthlyRate(annualRate);
        assertEquals(new BigDecimal("0.0100000000"), result); // 1% ao mês
    }

    @ParameterizedTest
    @CsvSource({
            "0.12, 0.0100000000",    // 12% anual = 1% mensal
            "0.24, 0.0200000000",    // 24% anual = 2% mensal
            "0.06, 0.0050000000",    // 6% anual = 0.5% mensal
            "0.036, 0.0030000000",    // 3.6% anual = 0.3% mensal
            "0.1854, 0.0154500000"    // 18.54% anual = 1.545% mensal
    })
    void calculateMonthlyRate_shouldHandleVariousRates(String annualRateStr, String expectedMonthlyRateStr) {
        BigDecimal annualRate = new BigDecimal(annualRateStr);
        BigDecimal result = calculator.calculateMonthlyRate(annualRate);
        assertEquals(new BigDecimal(expectedMonthlyRateStr), result);
    }

    // Testes para as novas faixas etárias e taxas de juros
    @Test
    void calculateMonthlyRate_shouldReturn5PercentForUpTo25Years() {
        BigDecimal annualRate = new BigDecimal("0.05"); // 5% ao ano
        BigDecimal result = calculator.calculateMonthlyRate(annualRate);
        assertEquals(new BigDecimal("0.0041666667"), result); // ~0.4167% ao mês
    }

    @Test
    void calculateMonthlyRate_shouldReturn3PercentFor26To40Years() {
        BigDecimal annualRate = new BigDecimal("0.03"); // 3% ao ano
        BigDecimal result = calculator.calculateMonthlyRate(annualRate);
        assertEquals(new BigDecimal("0.0025000000"), result); // 0.25% ao mês
    }

    @Test
    void calculateMonthlyRate_shouldReturn2PercentFor41To60Years() {
        BigDecimal annualRate = new BigDecimal("0.02"); // 2% ao ano
        BigDecimal result = calculator.calculateMonthlyRate(annualRate);
        assertEquals(new BigDecimal("0.0016666667"), result); // ~0.1667% ao mês
    }

    @Test
    void calculateMonthlyRate_shouldReturn4PercentForAbove60Years() {
        BigDecimal annualRate = new BigDecimal("0.04"); // 4% ao ano
        BigDecimal result = calculator.calculateMonthlyRate(annualRate);
        assertEquals(new BigDecimal("0.0033333333"), result); // ~0.3333% ao mês
    }

    @ParameterizedTest
    @CsvSource({
            // Até 25 anos: 5% ao ano (0.4167% ao mês)
            "1000.00, 0.0041666667, 12, 85.61",

            // De 26 a 40 anos: 3% ao ano (0.25% ao mês)
            "5000.00, 0.0025000000, 24, 214.91",

            // De 41 a 60 anos: 2% ao ano (0.1667% ao mês)
            "10000.00, 0.0016666667, 36, 286.43",

            // Acima de 60 anos: 4% ao ano (0.3333% ao mês)
            "2000.00, 0.0033333333, 6, 337.23"
    })
    void calculateMonthlyPayment_shouldHandleAgeBasedRates(
            String loanAmountStr,
            String monthlyRateStr,
            int termMonths,
            String expectedPaymentStr) {
        BigDecimal loanAmount = new BigDecimal(loanAmountStr);
        BigDecimal monthlyRate = new BigDecimal(monthlyRateStr);
        BigDecimal result = calculator.calculateMonthlyPayment(loanAmount, monthlyRate, termMonths);

        assertEquals(new BigDecimal(expectedPaymentStr), result);
    }

    @Test
    void calculateMonthlyPayment_shouldThrowExceptionWhenNullInput() {
        assertThrows(NullPointerException.class, () ->
                calculator.calculateMonthlyPayment(null, new BigDecimal("0.01"), 12));

        assertThrows(NullPointerException.class, () ->
                calculator.calculateMonthlyPayment(new BigDecimal("1000.00"), null, 12));
    }

}