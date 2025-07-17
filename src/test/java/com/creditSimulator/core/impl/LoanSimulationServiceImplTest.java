package com.creditSimulator.core.impl;

import com.creditsimulator.core.InterestCalculator;
import com.creditsimulator.core.impl.LoanSimulationServiceImpl;
import com.creditsimulator.domain.enums.AgeBracketEnum;
import com.creditsimulator.domain.exception.LoanSimulationException;
import com.creditsimulator.domain.model.simulation.LoanSimulationRequestModel;
import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import com.creditsimulator.redis.service.LoanSimulationCacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanSimulationServiceImplTest {

    @Mock
    private InterestCalculator interestCalculator;

    @Mock
    private LoanSimulationCacheService cacheService;

    @InjectMocks
    private LoanSimulationServiceImpl loanSimulationService;

    @Test
    void simulateLoan_shouldReturnValidResponseModel() throws LoanSimulationException {
        LoanSimulationRequestModel request = createRequestModel(25, new BigDecimal("10000.00"), 12);

        when(interestCalculator.calculateMonthlyRate(any())).thenReturn(new BigDecimal("0.004166667"));
        when(interestCalculator.calculateMonthlyPayment(any(), any(), anyInt()))
                .thenReturn(new BigDecimal("856.07"));

        LoanSimulationResponseModel result = loanSimulationService.simulateLoan(request);

        assertNotNull(result);
        assertEquals(request.getAmount(), result.getAmount());
        assertEquals(request.getMonths(), result.getMonths());
        assertEquals(new BigDecimal("856.07"), result.getMonthlyPayment());
        assertEquals(new BigDecimal("10272.84"), result.getTotalAmount()); // 856.07 * 12
        assertEquals(new BigDecimal("272.84"), result.getTotalInterest()); // 10272.84 - 10000.00
        verify(cacheService).saveSimulationToCache(any(), any());
    }

    @ParameterizedTest
    @MethodSource("ageAndRateProvider")
    void simulateLoan_shouldUseCorrectRateForAgeBracket(int age, BigDecimal expectedRate) throws LoanSimulationException {
        LoanSimulationRequestModel request = createRequestModel(age, new BigDecimal("10000.00"), 12);

        when(interestCalculator.calculateMonthlyRate(expectedRate)).thenReturn(new BigDecimal("0.01"));
        when(interestCalculator.calculateMonthlyPayment(any(), any(), anyInt()))
                .thenReturn(new BigDecimal("1000.00"));

        LoanSimulationResponseModel result = loanSimulationService.simulateLoan(request);

        verify(interestCalculator).calculateMonthlyRate(expectedRate);
    }

    @Test
    void simulateLoan_shouldThrowExceptionWhenCalculationFails() {
        LoanSimulationRequestModel request = createRequestModel(30, new BigDecimal("10000.00"), 12);

        when(interestCalculator.calculateMonthlyRate(any()))
                .thenThrow(new ArithmeticException("Calculation error"));

        assertThrows(LoanSimulationException.class, () ->
                loanSimulationService.simulateLoan(request));
    }

    @Test
    void simulateLoan_shouldSaveToCacheWithValidData() throws LoanSimulationException {
        LoanSimulationRequestModel request = createRequestModel(25, new BigDecimal("5000.00"), 6);

        when(interestCalculator.calculateMonthlyRate(any())).thenReturn(new BigDecimal("0.004166667"));
        when(interestCalculator.calculateMonthlyPayment(any(), any(), anyInt()))
                .thenReturn(new BigDecimal("856.07"));

        LoanSimulationResponseModel result = loanSimulationService.simulateLoan(request);

        verify(cacheService).saveSimulationToCache(result.getE2e(), result);
        assertNotNull(result.getE2e());
    }

    private static Stream<Arguments> ageAndRateProvider() {
        return Stream.of(
                Arguments.of(20, AgeBracketEnum.YOUNG.getRate()),
                Arguments.of(30, AgeBracketEnum.ADULT.getRate()),
                Arguments.of(49, AgeBracketEnum.MIDDLE_AGED.getRate())
        );
    }

    private LoanSimulationRequestModel createRequestModel(int age, BigDecimal amount, int months) {
        LoanSimulationRequestModel request = new LoanSimulationRequestModel();
        request.setBirthDate(LocalDate.now().minusYears(age));
        request.setAmount(amount);
        request.setMonths(months);
        return request;
    }
}