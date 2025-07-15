package com.creditsimulator.rest.message.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BankAccountDetailsDTO(
        @JsonProperty("banco")
        String bankCode,
        @JsonProperty("agencia")
        String branchNumber,
        @JsonProperty("conta")
        String accountNumber,
        @JsonProperty("tipo_conta")
        String accountType) {
}
