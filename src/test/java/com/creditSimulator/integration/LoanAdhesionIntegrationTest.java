package com.creditSimulator.integration;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoanAdhesionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String CLIENT_ID = "123e4567-e89b-12d3-a456-426614174000";

    @Test
    void shouldCompleteLoanSimulationAndAdhesionSuccessfully() throws Exception {
        var simulationResult = mockMvc.perform(post("/emprestimo/simulacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "valor_solicitado": "5000.00",
                                  "data_nascimento": "1958-05-15",
                                  "total_meses": 24
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String simulationId = JsonPath.read(simulationResult.getResponse().getContentAsString(), "$.e2e");

        String adhesionRequestBody = String.format("""
                {
                  "id_simulacao": "%s",
                  "id_cliente": "%s",
                  "data_primeiro_vencimento": "2025-12-05",
                  "metodo_pagamento": "CARTAO_CREDITO"
                }
                """, simulationId, CLIENT_ID);

        mockMvc.perform(post("/emprestimo/adesao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adhesionRequestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenFirstPaymentDateExceedsFiveMonths() throws Exception {
        String requestBody = """
                {
                  "id_simulacao": "123",
                  "id_cliente": "123e4567-e89b-12d3-a456-426614174000",
                  "data_primeiro_vencimento": "2026-01-05",
                  "metodo_pagamento": "CARTAO_CREDITO"
                }
                """;

        mockMvc.perform(post("/emprestimo/adesao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPaymentMethodIsDebitAutomaticAndBankDetailsMissing() throws Exception {
        String requestBody = """
                {
                  "id_simulacao": "123",
                  "id_cliente": "123e4567-e89b-12d3-a456-426614174000",
                  "data_primeiro_vencimento": "2025-12-05",
                  "metodo_pagamento": "DEBITO_AUTOMATICO"
                }
                """;

        mockMvc.perform(post("/emprestimo/adesao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
