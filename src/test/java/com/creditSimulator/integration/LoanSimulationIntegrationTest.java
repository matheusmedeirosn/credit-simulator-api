package com.creditSimulator.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoanSimulationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final int DECIMAL_SCALE = 2;

    private BigDecimal getAnnualInterestRateByAge(int age) {
        if (age <= 25) return BigDecimal.valueOf(0.05);
        if (age <= 40) return BigDecimal.valueOf(0.03);
        if (age <= 60) return BigDecimal.valueOf(0.02);
        return BigDecimal.valueOf(0.04);
    }

    private BigDecimal calculateMonthlyRate(BigDecimal annualRate) {
        return annualRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
    }

    @Test
    void shouldReturnCorrectMonthlyPaymentForAge58() throws Exception {
        String requestBody = """
                {
                  "valor_solicitado": 5000.00,
                  "data_nascimento": "1958-05-15",
                  "total_meses": 24
                }
                """;

        BigDecimal annualRate = getAnnualInterestRateByAge(2025 - 1958);
        BigDecimal monthlyRate = calculateMonthlyRate(annualRate);

        BigDecimal expectedMonthlyPayment = BigDecimal.valueOf(217.12);

        mockMvc.perform(post("/emprestimo/simulacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parcela_mensal").value(expectedMonthlyPayment.doubleValue()));
    }

    @Test
    void shouldReturnCorrectMonthlyPaymentForAge22() throws Exception {
        String requestBody = """
                {
                  "valor_solicitado": 10000.00,
                  "data_nascimento": "2003-07-17",
                  "total_meses": 12
                }
                """;

        BigDecimal annualRate = getAnnualInterestRateByAge(2025 - 2003);
        BigDecimal monthlyRate = calculateMonthlyRate(annualRate);

        BigDecimal expectedMonthlyPayment = BigDecimal.valueOf(856.07);

        mockMvc.perform(post("/emprestimo/simulacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parcela_mensal").value(expectedMonthlyPayment.doubleValue()));
    }

    @Test
    void shouldReturnCorrectMonthlyPaymentForAge35() throws Exception {
        String requestBody = """
                {
                  "valor_solicitado": 15000.00,
                  "data_nascimento": "1990-01-01",
                  "total_meses": 36
                }
                """;

        BigDecimal annualRate = getAnnualInterestRateByAge(2025 - 1990);
        BigDecimal monthlyRate = calculateMonthlyRate(annualRate);

        BigDecimal expectedMonthlyPayment = BigDecimal.valueOf(436.22);

        mockMvc.perform(post("/emprestimo/simulacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parcela_mensal").value(expectedMonthlyPayment.doubleValue()));
    }

    @Test
    void shouldReturnCorrectMonthlyPaymentForAge65() throws Exception {
        String requestBody = """
                {
                  "valor_solicitado": 8000.00,
                  "data_nascimento": "1960-03-25",
                  "total_meses": 18
                }
                """;

        BigDecimal annualRate = getAnnualInterestRateByAge(2025 - 1960);
        BigDecimal monthlyRate = calculateMonthlyRate(annualRate);

        BigDecimal expectedMonthlyPayment = BigDecimal.valueOf(458.65);

        mockMvc.perform(post("/emprestimo/simulacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parcela_mensal").value(expectedMonthlyPayment.doubleValue()));
    }
}
