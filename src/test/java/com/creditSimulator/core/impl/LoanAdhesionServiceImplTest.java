package com.creditSimulator.core.impl;

import com.creditsimulator.core.impl.LoanAdhesionServiceImpl;
import com.creditsimulator.domain.exception.SimulationNotFoundException;
import com.creditsimulator.domain.model.adhesion.LoanAdhesionRequestModel;
import com.creditsimulator.domain.model.adhesion.LoanAdhesionResponseModel;
import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import com.creditsimulator.rabbitMQ.producer.LoanMessageProducer;
import com.creditsimulator.redis.service.LoanSimulationCacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanAdhesionServiceImplTest {

    @Mock
    private LoanSimulationCacheService cacheService;

    @Mock
    private LoanMessageProducer messageProducer;

    @InjectMocks
    private LoanAdhesionServiceImpl loanAdhesionService;

    @Test
    void simulateLoan_shouldReturnResponseModelWhenSimulationExists() {
        String simulationId = UUID.randomUUID().toString();
        LoanAdhesionRequestModel requestModel = new LoanAdhesionRequestModel();
        requestModel.setSimulationId(simulationId);

        LoanSimulationResponseModel simulation = createMockSimulation(simulationId);
        when(cacheService.getSimulation(simulationId)).thenReturn(Optional.of(simulation));

        LoanAdhesionResponseModel result = loanAdhesionService.simulateLoan(requestModel);

        assertNotNull(result);
        assertEquals(simulationId, result.getSimulationId());
        verify(cacheService).getSimulation(simulationId);
        verify(messageProducer).sendToQueue(requestModel);
    }

    @Test
    void simulateLoan_shouldThrowExceptionWhenSimulationNotFound() {
        String simulationId = UUID.randomUUID().toString();
        LoanAdhesionRequestModel requestModel = new LoanAdhesionRequestModel();
        requestModel.setSimulationId(simulationId);

        when(cacheService.getSimulation(simulationId)).thenReturn(Optional.empty());

        SimulationNotFoundException exception = assertThrows(SimulationNotFoundException.class,
                () -> loanAdhesionService.simulateLoan(requestModel));

        assertEquals("Simulação de ID " + simulationId + "não encontrada ou expirada", exception.getMessage());
        verify(cacheService).getSimulation(simulationId);
        verifyNoInteractions(messageProducer);
    }

    @Test
    void simulateLoan_shouldCopySimulationDataToRequestModel() {
        String simulationId = UUID.randomUUID().toString();
        LoanAdhesionRequestModel requestModel = new LoanAdhesionRequestModel();
        requestModel.setSimulationId(simulationId);

        LoanSimulationResponseModel simulation = createMockSimulation(simulationId);
        when(cacheService.getSimulation(simulationId)).thenReturn(Optional.of(simulation));

        loanAdhesionService.simulateLoan(requestModel);

        assertEquals(simulation.getMonthlyPayment(), requestModel.getMonthlyPayment());
        assertEquals(simulation.getTotalInterest(), requestModel.getTotalInterest());
        assertEquals(simulation.getTotalAmount(), requestModel.getTotalAmount());
        assertEquals(simulation.getAnnualInterestRate(), requestModel.getAnnualInterestRate());
    }

    @Test
    void simulateLoan_shouldSendRequestModelToQueue() {
        String simulationId = UUID.randomUUID().toString();
        LoanAdhesionRequestModel requestModel = new LoanAdhesionRequestModel();
        requestModel.setSimulationId(simulationId);

        LoanSimulationResponseModel simulation = createMockSimulation(simulationId);
        when(cacheService.getSimulation(simulationId)).thenReturn(Optional.of(simulation));

        loanAdhesionService.simulateLoan(requestModel);

        verify(messageProducer).sendToQueue(requestModel);
    }

    @Test
    void simulateLoan_shouldMapRequestToResponseCorrectly() {
        String simulationId = UUID.randomUUID().toString();
        LoanAdhesionRequestModel requestModel = new LoanAdhesionRequestModel();
        requestModel.setSimulationId(simulationId);
        requestModel.setMonthlyPayment(new BigDecimal("888.49"));
        requestModel.setTotalInterest(new BigDecimal("100.49"));
        requestModel.setTotalAmount(new BigDecimal("10100.49"));
        requestModel.setAnnualInterestRate(new BigDecimal("0.12")); // 12%

        LoanSimulationResponseModel simulation = createMockSimulation(simulationId);
        when(cacheService.getSimulation(simulationId)).thenReturn(Optional.of(simulation));

        LoanAdhesionResponseModel result = loanAdhesionService.simulateLoan(requestModel);

        assertEquals(requestModel.getSimulationId(), result.getSimulationId());

        assertEquals(requestModel.getMonthlyPayment(), result.getMonthlyPayment());
        assertEquals(requestModel.getTotalInterest(), result.getTotalInterest());
        assertEquals(requestModel.getTotalAmount(), result.getTotalAmount());
        assertEquals(requestModel.getAnnualInterestRate(), result.getAnnualInterestRate());
    }

    private LoanSimulationResponseModel createMockSimulation(String e2eId) {
        return new LoanSimulationResponseModel(
                new BigDecimal("10000.00"),
                12,
                new BigDecimal("888.49"),
                new BigDecimal("100.49"),
                new BigDecimal("10100.49"),
                new BigDecimal("0.12")
        );
    }
}