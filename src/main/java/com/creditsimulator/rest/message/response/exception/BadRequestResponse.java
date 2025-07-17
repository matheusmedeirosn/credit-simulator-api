package com.creditsimulator.rest.message.response.exception;

import com.creditsimulator.rest.message.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
@Schema(description = "Contempla o retorno da execução.")
public class BadRequestResponse extends BaseResponse {

    @JsonProperty("inconsistencias")
    @Schema(description = "Lista de inconsistências da requisição", name = "inconsistencias")
    private List<Inconsistency> inconsistencies;

    @Builder
    public BadRequestResponse(String messageCode, String message, List<Inconsistency> inconsistencies) {
        super(messageCode, message);
        this.inconsistencies = inconsistencies;
    }

}
