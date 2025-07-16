package com.creditsimulator.rest.message.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.creditsimulator.rest.utils.RestConstants.VALIDATION_NOT_BLANK;


@Schema(description = "Detalhes da conta bancária para operações financeiras")
public record BankAccountDetailsDTO(
        @JsonProperty("banco")
        @Schema(
                description = "Código do banco (composto por 3 dígitos)",
                example = "001",
                minLength = 3,
                maxLength = 3
        )
        @Pattern(regexp = "^\\d{3}$", message = "O código do banco deve conter exatamente 3 dígitos")
        @NotBlank(message = VALIDATION_NOT_BLANK)
        @Size(min = 3, max = 3)
        String bankCode,

        @JsonProperty("agencia")
        @Schema(
                description = "Número da agência bancária (sem dígito verificador)",
                example = "1234",
                minLength = 1,
                maxLength = 5
        )
        @Pattern(regexp = "^\\d{1,5}$", message = "O número da agência deve conter apenas dígitos (1-5 caracteres)")
        @NotBlank(message = VALIDATION_NOT_BLANK)
        @Size(min = 1, max = 5)
        String branchNumber,

        @JsonProperty("conta")
        @Schema(
                description = "Número da conta bancária (sem dígito verificador)",
                example = "987654",
                minLength = 1,
                maxLength = 10
        )
        @Pattern(regexp = "^\\d{1,10}$", message = "O número da conta deve conter apenas dígitos (1-10 caracteres)")
        @NotBlank(message = VALIDATION_NOT_BLANK)
        @Size(min = 1, max = 10)
        String accountNumber,

        @JsonProperty("tipo_conta")
        @Schema(
                description = "Tipo de conta bancária",
                example = "CORRENTE",
                allowableValues = {"CORRENTE", "POUPANCA", "SALARIO", "PAGAMENTOS"}
        )
        @Pattern(regexp = "^(CORRENTE|POUPANCA|SALARIO|PAGAMENTOS)$",
                message = "Tipo de conta inválido. Valores permitidos: CORRENTE, POUPANCA, SALARIO, PAGAMENTOS")
        @NotBlank(message = VALIDATION_NOT_BLANK)
        String accountType) {
}