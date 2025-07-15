package com.creditsimulator.rest.message.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import static com.creditsimulator.rest.utils.RestConstants.*;

public record LoanRequest(
        @Schema(
                description = "Valor solicitado para o empréstimo",
                example = EXAMPLE_AMOUNT,
                minimum = MIN_VALUE_AMOUNT
        )
        @NotNull(message = VALIDATION_NOT_NULL)
        @Positive(message = VALIDATION_POSITIVE)
        @DecimalMin(value = MIN_VALUE_AMOUNT, message = VALIDATION_VALUE_MIN + MIN_VALUE_AMOUNT)
        @JsonProperty("valor_solicitado")
        BigDecimal amount,

        @Schema(
                description = "Data de nascimento do solicitante",
                example = EXAMPLE_BIRTHDATE
        )
        @NotBlank(message = VALIDATION_NOT_BLANK)
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = VALIDATION_PATTERN_DATE_BIRTH)
        @JsonProperty("data_nascimento")
        String birthDate,

        @Schema(
                description = "Prazo do empréstimo em meses",
                example = EXAMPLE_MONTHS,
                minimum = MIN_VALUE_MONTHS,
                maximum = MAX_VALUE_MONTHS
        )
        @NotNull(message = VALIDATION_NOT_NULL) @Min(value = 1, message = VALIDATION_VALUE_MIN + MIN_VALUE_MONTHS)
        @JsonProperty("total_meses")
        Integer months
) {
}
