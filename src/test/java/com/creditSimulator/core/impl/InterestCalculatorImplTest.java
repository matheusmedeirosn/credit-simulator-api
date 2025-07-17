package com.creditSimulator.core.impl;

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
    void calculateMonthlyRate_ShouldDivideAnnualRateBy12() {
        BigDecimal annualRate = new BigDecimal("0.12"); // 12% ao ano

        BigDecimal monthlyRate = calculator.calculateMonthlyRate(annualRate);

        assertEquals(new BigDecimal("0.0100000000"), monthlyRate);
        assertEquals(10, monthlyRate.scale());
    }

    @ParameterizedTest
    @CsvSource({
            "0.12, 0.0100000000",    // 12% a.a. -> 1% a.m.
            "0.06, 0.0050000000",     // 6% a.a. -> 0.5% a.m.
            "0.24, 0.0200000000",     // 24% a.a. -> 2% a.m.
            "0.00, 0.0000000000",     // 0% a.a. -> 0% a.m.
            "1.00, 0.0833333333"      // 100% a.a. -> ~8.33% a.m.
    })
    void calculateMonthlyRate_WithVariousRates_ShouldReturnCorrectValues(
            String annualRateStr, String expectedMonthlyRateStr) {
        BigDecimal annualRate = new BigDecimal(annualRateStr);

        BigDecimal result = calculator.calculateMonthlyRate(annualRate);

        assertEquals(new BigDecimal(expectedMonthlyRateStr), result);
    }

    @Test
    void calculateMonthlyPayment_ShouldCalculateCorrectPayment() {
        BigDecimal loanAmount = new BigDecimal("10000.00");
        BigDecimal monthlyRate = new BigDecimal("0.01"); // 1% ao mês
        int termMonths = 12;

        BigDecimal payment = calculator.calculateMonthlyPayment(loanAmount, monthlyRate, termMonths);

        assertEquals(new BigDecimal("888.49"), payment);
        assertEquals(2, payment.scale());
    }

    @ParameterizedTest
    @CsvSource({
            "10000.00, 0.05, 12, 856.07",
            "5000.00, 0.04, 24, 217.12",
            "20000.00, 0.03, 36, 581.62",
            "15000.00, 0.02, 12, 1263.58"
    })
    void calculateMonthlyPayment_WithVariousInputs_ShouldReturnCorrectValues(
            String amountStr, String rateStr, int term, String expectedPaymentStr) {
        BigDecimal amount = new BigDecimal(amountStr);
        BigDecimal annualRate = new BigDecimal(rateStr);

        BigDecimal monthlyRate = calculator.calculateMonthlyRate(annualRate);

        BigDecimal result = calculator.calculateMonthlyPayment(amount, monthlyRate, term);

        assertEquals(new BigDecimal(expectedPaymentStr), result);
    }

    @Test
    void calculateMonthlyPayment_WithZeroTerm_ShouldThrowException() {
        // Arrange
        BigDecimal amount = new BigDecimal("1000.00");
        BigDecimal rate = new BigDecimal("0.01");
        int term = 0;

        // Act & Assert
        assertThrows(ArithmeticException.class, () -> {
            calculator.calculateMonthlyPayment(amount, rate, term);
        });
    }

    @Test
    void calculateMonthlyPayment_WithNullValues_ShouldThrowNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            calculator.calculateMonthlyPayment(null, new BigDecimal("0.01"), 12);
        });

        assertThrows(NullPointerException.class, () -> {
            calculator.calculateMonthlyPayment(new BigDecimal("1000.00"), null, 12);
        });
    }
}