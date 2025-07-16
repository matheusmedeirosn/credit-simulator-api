package com.creditSimulator.rest.controller;

import com.creditsimulator.core.LoanAdhesionService;
import com.creditsimulator.domain.enums.PaymentMethod;
import com.creditsimulator.domain.model.adhesion.LoanAdhesionResponseModel;
import com.creditsimulator.rest.controller.LoanAdhesionController;
import com.creditsimulator.rest.message.request.BankAccountDetailsDTO;
import com.creditsimulator.rest.message.request.LoanAdhesionRequestDTO;
import com.creditsimulator.rest.message.response.LoanAdhesionResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanAdhesionControllerTest {

    @Mock
    private LoanAdhesionService adhesionService;

    @InjectMocks
    private LoanAdhesionController loanAdhesionController;

    private LoanAdhesionRequestDTO createValidRequest() {
        return new LoanAdhesionRequestDTO(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDate.now().plusMonths(1),
                "DEBITO_AUTOMATICO",
                new BankAccountDetailsDTO("123", "456", "789", "CACC")
        );
    }

    @Test
    void adhesionLoan_WithValidRequest_ShouldReturnOkResponse() {
        var requestDTO = createValidRequest();
        LoanAdhesionResponseModel responseModel = LoanAdhesionResponseModel.builder()
                .amount(new BigDecimal("13510.00"))
                .months(12)

                .simulationId("SIM-123456")
                .contractDate(LocalDate.of(2023, 11, 15))
                .firstDueDate(LocalDate.of(2023, 12, 15))
                .monthlyPayment(new BigDecimal("1250.75"))
                .paymentMethod(PaymentMethod.DEBITO_AUTOMATICO)
                .totalInterest(new BigDecimal("1502.25"))
                .totalAmount(new BigDecimal("15012.25"))
                .annualInterestRate(new BigDecimal("9.99"))
                .amount(new BigDecimal("13510.00"))
                .build();

        var expectedResponse = new LoanAdhesionResponseDTO(
                responseModel.getSimulationId(),
                responseModel.getContractDate(),
                responseModel.getFirstDueDate(),
                responseModel.getMonthlyPayment(),
                responseModel.getAmount(),
                responseModel.getTotalInterest(),
                responseModel.getTotalAmount(),
                responseModel.getAnnualInterestRate(),
                responseModel.getMonths(),
                responseModel.getPaymentMethod().toString()
        );

        when(adhesionService.simulateLoan(any())).thenReturn(responseModel);

        ResponseEntity<LoanAdhesionResponseDTO> response =
                loanAdhesionController.adhesionLoan(requestDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());

        verify(adhesionService).simulateLoan(any());
    }

    @Test
    void adhesionLoan_WhenServiceThrowsException_ShouldPropagate() {
        var requestDTO = createValidRequest();
        when(adhesionService.simulateLoan(any())).thenThrow(new RuntimeException("Service error"));

        assertThrows(RuntimeException.class, () -> {
            loanAdhesionController.adhesionLoan(requestDTO);
        });

        verify(adhesionService).simulateLoan(any());
    }
}