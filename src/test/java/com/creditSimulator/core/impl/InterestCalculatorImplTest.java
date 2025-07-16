package com.creditSimulator.core.impl;

import com.creditsimulator.core.impl.InterestCalculatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InterestCalculatorImplTest {

    private final InterestCalculatorImpl calculator = new InterestCalculatorImpl();

    @Test
    void calculateMonthlyRate_ShouldDivideAnnualRateBy12() {
        // Arrange
        BigDecimal annualRate = new BigDecimal("0.12"); // 12% ao ano

        // Act
        BigDecimal monthlyRate = calculator.calculateMonthlyRate(annualRate);

        // Assert
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
        // Arrange
        BigDecimal annualRate = new BigDecimal(annualRateStr);

        // Act
        BigDecimal result = calculator.calculateMonthlyRate(annualRate);

        // Assert
        assertEquals(new BigDecimal(expectedMonthlyRateStr), result);
    }

    @Test
    void calculateMonthlyPayment_ShouldCalculateCorrectPayment() {
        // Arrange
        BigDecimal loanAmount = new BigDecimal("10000.00");
        BigDecimal monthlyRate = new BigDecimal("0.01"); // 1% ao mês
        int termMonths = 12;

        // Act
        BigDecimal payment = calculator.calculateMonthlyPayment(loanAmount, monthlyRate, termMonths);

        // Assert
        assertEquals(new BigDecimal("888.49"), payment);
        assertEquals(2, payment.scale());
    }

    @ParameterizedTest
    @CsvSource({
            "10000.00, 0.01, 12, 888.49",     // Empréstimo padrão
            "5000.00, 0.02, 6, 912.25",       // Valor menor, taxa maior
            "20000.00, 0.005, 24, 885.72",    // Valor maior, taxa menor
            "1500.00, 0.03, 3, 530.03",       // Prazo curto
            "1000.00, 0.00, 5, 200.00"        // Taxa zero (sem juros)
    })
    void calculateMonthlyPayment_WithVariousInputs_ShouldReturnCorrectValues(
            String amountStr, String rateStr, int term, String expectedPaymentStr) {
        // Arrange
        BigDecimal amount = new BigDecimal(amountStr);
        BigDecimal rate = new BigDecimal(rateStr);

        // Act
        BigDecimal result = calculator.calculateMonthlyPayment(amount, rate, term);

        // Assert
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