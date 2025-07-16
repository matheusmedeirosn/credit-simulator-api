package com.creditSimulator.rest.controller;

import com.creditsimulator.core.LoanSimulationService;
import com.creditsimulator.domain.exception.LoanSimulationException;
import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import com.creditsimulator.rest.controller.LoanSimulationController;
import com.creditsimulator.rest.message.request.LoanSimulationRequestDTO;
import com.creditsimulator.rest.message.response.LoanSimulationResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanSimulationControllerTest {

    @Mock
    private LoanSimulationService simulationService;

    @InjectMocks
    private LoanSimulationController loanSimulationController;

    private LoanSimulationRequestDTO createValidRequest() {
        return new LoanSimulationRequestDTO(
                new BigDecimal("10000.00"),
                "1990-01-01",
                12
        );
    }

    @Test
    void simulateLoan_WithValidRequest_ShouldReturnOk() throws LoanSimulationException {
        var requestDTO = createValidRequest();
        var responseModel = new LoanSimulationResponseModel(
                new BigDecimal("10000.00"),
                12,
                new BigDecimal("888.49"),
                new BigDecimal("100.49"),
                new BigDecimal("10100.49"),
                new BigDecimal("0.12")
        );

        var expectedResponse = new LoanSimulationResponseDTO(
                responseModel.getAmount(),
                responseModel.getMonthlyPayment(),
                responseModel.getTotalInterest(),
                responseModel.getTotalAmount(),
                responseModel.getAnnualInterestRate(),
                responseModel.getMonths(),
                responseModel.getE2e()
        );

        when(simulationService.simulateLoan(any())).thenReturn(responseModel);

        ResponseEntity<LoanSimulationResponseDTO> response =
                loanSimulationController.simulateLoan(requestDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(simulationService).simulateLoan(any());
    }

    @Test
    void simulateLoan_WhenServiceThrowsException_ShouldPropagate() throws LoanSimulationException {
        var requestDTO = createValidRequest();
        when(simulationService.simulateLoan(any()))
                .thenThrow(new LoanSimulationException("Erro na simulação"));

        assertThrows(LoanSimulationException.class, () -> {
            loanSimulationController.simulateLoan(requestDTO);
        });
    }
}