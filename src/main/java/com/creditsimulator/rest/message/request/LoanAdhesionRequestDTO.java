package com.creditsimulator.rest.message.request;

import com.creditsimulator.rest.validate.BankAccountDetailsRequired;
import com.creditsimulator.rest.validate.ValidFirstDueDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

import static com.creditsimulator.rest.utils.RestConstants.*;

@BankAccountDetailsRequired
public record LoanAdhesionRequestDTO(
        @Schema(
                description = "ID único do contrato gerado",
                example = EXAMPLE_UUID,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = VALIDATION_NOT_BLANK)
        @JsonProperty("id_simulacao")
        String simulationId,

        @Schema(
                description = "ID único do cliente cadastrado",
                example = EXAMPLE_UUID,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty("id_cliente")
        @NotBlank String customerId,

        @Schema(
                description = "Data do vencimento",
                example = EXAMPLE_DATE,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @ValidFirstDueDate
        @JsonProperty("data_primeiro_vencimento")
        @NotNull @Future LocalDate firstDueDate,

        @Schema(
                description = "Meio de pagamento escolhido",
                allowableValues = "DEBITO_AUTOMATICO,BOLETO_BANCARIO,CARTAO_CREDITO,TRANSFERENCIA_PIX",
                example = EXAMPLE_PAYMENT_METHOD,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Pattern(regexp = "DEBITO_AUTOMATICO|BOLETO_BANCARIO|CARTAO_CREDITO|TRANSFERENCIA_PIX")
        @JsonProperty("metodo_pagamento")
        @NotNull String paymentMethod,

        @Schema(
                description = "Detalhes bancários. Deve ser obrigatório se o meio de pagamento for débito automático",
                example = EXAMPLE_PAYMENT_METHOD,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Valid
        @JsonProperty("detalhes_bancarios")
        BankAccountDetailsDTO bankAccountDetails

) {
}
