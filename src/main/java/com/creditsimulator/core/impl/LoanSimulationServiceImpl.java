package com.creditsimulator.core.impl;

import com.creditsimulator.core.InterestCalculator;
import com.creditsimulator.core.LoanSimulationService;
import com.creditsimulator.domain.enums.AgeBracketEnum;
import com.creditsimulator.domain.model.simulation.LoanSimulationRequestModel;
import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import com.creditsimulator.redis.service.LoanSimulationCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class LoanSimulationServiceImpl implements LoanSimulationService {

    private final InterestCalculator interestCalculator;
    private final LoanSimulationCacheService cacheService;

    @Override
    public LoanSimulationResponseModel simulateLoan(LoanSimulationRequestModel request) {
        BigDecimal annualInterestRate = determineAnnualInterestRate(request.getBirthDate());
        BigDecimal monthlyInterestRate = interestCalculator.calculateMonthlyRate(annualInterestRate);

        BigDecimal monthlyPayment = calculateMonthlyPayment(
                request.getAmount(),
                monthlyInterestRate,
                request.getMonths()
        );

        LoanSimulationResponseModel responseModel=  buildResponse(request.getAmount(), monthlyPayment, request.getMonths(), annualInterestRate);
        publishCacheSimulation(responseModel);
        return responseModel;
    }

    private void publishCacheSimulation (LoanSimulationResponseModel responseModel){
        cacheService.saveSimulationToCache(responseModel.getE2e(), responseModel);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal loanAmount, BigDecimal monthlyRate, int termMonths) {
        return interestCalculator.calculateMonthlyPayment(
                loanAmount,
                monthlyRate,
                termMonths
        );
    }

    private BigDecimal determineAnnualInterestRate(LocalDate birthDate){
        int age = calculateAge(birthDate);
        return AgeBracketEnum.getRate(age);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private LoanSimulationResponseModel buildResponse(
            BigDecimal loanAmount,
            BigDecimal monthlyPayment,
            int termMonths,
            BigDecimal annualInterestRate
    ) {
        // 1. Cálculo do valor total a ser pago
        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(termMonths));

        // 2. Cálculo do total de juros pagos
        BigDecimal totalInterest = totalAmount.subtract(loanAmount);

        // 3. Construção do objeto de resposta
        return new LoanSimulationResponseModel(
                loanAmount,                      // valor do empréstimo
                termMonths,                      // prazo em meses
                monthlyPayment,                  // Parcela mensal (já arredondada)
                totalInterest.setScale(2, RoundingMode.HALF_UP),  // Juros totais com 2 decimais
                totalAmount.setScale(2, RoundingMode.HALF_UP),    // Valor total com 2 decimais
                annualInterestRate               // Taxa anual usada
        );    }

}
