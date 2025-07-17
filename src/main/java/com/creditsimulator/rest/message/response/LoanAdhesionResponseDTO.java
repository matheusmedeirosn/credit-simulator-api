package com.creditsimulator.rest.message.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.creditsimulator.rest.utils.RestConstants.*;

@Schema(description = "Resposta de adesão ao empréstimo")
public record LoanAdhesionResponseDTO(

        @Schema(
                description = "ID único do contrato gerado",
                example = EXAMPLE_UUID,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("e2e")
        String simulationId,

        @Schema(
                description = "Data de criação do contrato",
                example = EXAMPLE_DATE,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("data_criacao")
        LocalDate contractDate,

        @Schema(
                description = "Data do primeiro vencimento",
                example = EXAMPLE_DATE,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("data_primeiro_vencimento")
        LocalDate firstDueDate,

        @Schema(
                description = "Valor da parcela mensal",
                example = EXAMPLE_AMOUNT,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("parcela_mensal")
        BigDecimal monthlyPayment,

        @Schema(
                description = "Valor original solicitado para o empréstimo",
                example = EXAMPLE_AMOUNT,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("valor_solicitado")
        BigDecimal amount,

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

        @Schema(
                description = "Meio de pagamento escolhido",
                allowableValues = "DEBITO_AUTOMATICO,BOLETO_BANCARIO,CARTAO_CREDITO,TRANSFERENCIA_PIX",
                example = EXAMPLE_PAYMENT_METHOD,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("metodo_pagamento")
        String paymentMethod

) {
}
