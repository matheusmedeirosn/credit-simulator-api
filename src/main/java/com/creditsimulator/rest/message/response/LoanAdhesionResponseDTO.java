package com.creditsimulator.rest.message.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.creditsimulator.rest.utils.RestConstants.*;

@Schema(description = "Resposta de adesão ao empréstimo")
public record LoanAdhesionResponseDTO(

        @Schema(
                description = "ID único do contrato gerado",
                example = EXAMPLE_UUID,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        UUID contractId,

        @Schema(
                description = "Data de criação do contrato",
                example = EXAMPLE_DATE,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDate contractDate,

        @Schema(
                description = "Data do primeiro vencimento",
                example = EXAMPLE_DATE,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDate firstDueDate,

        @Schema(
                description = "Valor da parcela mensal",
                example = EXAMPLE_AMOUNT,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        BigDecimal installmentAmount,

        @Schema(
                description = "Meio de pagamento escolhido",
                allowableValues = "DEBITO_AUTOMATICO,BOLETO_BANCARIO,CARTAO_CREDITO,TRANSFERENCIA_PIX",
                example = EXAMPLE_PAYMENT_METHOD,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String paymentMethod

) {
}
