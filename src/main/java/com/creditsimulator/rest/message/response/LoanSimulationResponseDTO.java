package com.creditsimulator.rest.message.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

import static com.creditsimulator.rest.utils.RestConstants.*;

@Schema(description = "Resposta detalhada da simulação de empréstimo")
public record LoanSimulationResponseDTO(
        @Schema(
                description = "Valor original solicitado para o empréstimo",
                example = EXAMPLE_AMOUNT,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("valor_solicitado")
        BigDecimal amount,

        @Schema(
                description = "Valor calculado de cada parcela mensal",
                example = EXAMPLE_AMOUNT,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("parcela_mensal")
        BigDecimal monthlyPayment,

        @Schema(
                description = "Total de juros acumulados durante todo o período",
                example = EXAMPLE_AMOUNT,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("total_juros")
        BigDecimal totalInterest,

        @Schema(
                description = "Valor total a ser pago (capital + juros)",
                example = EXAMPLE_AMOUNT,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("valor_total")
        BigDecimal totalAmount,

        @Schema(
                description = "Taxa de juros anual aplicada, baseada na idade do cliente",
                example = EXAMPLE_ANNUAL_RATE,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("taxa_juros_anual")
        BigDecimal annualInterestRate,

        @Schema(
                description = "Prazo do empréstimo em meses",
                example = MAX_VALUE_MONTHS,
                minimum = MIN_VALUE_MONTHS,
                maximum = MAX_VALUE_MONTHS,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("prazo_meses")
        Integer months,

        String e2e
) {
}