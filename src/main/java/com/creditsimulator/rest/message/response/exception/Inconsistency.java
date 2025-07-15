package com.creditsimulator.rest.message.response.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Descreve a inconsistência apresentada por um campo enviado na requisição", name = "inconsistencia")
@Data
@Builder
public class Inconsistency {

    @Schema(description = "Nome do campo da inconsistência", example = "valor_solicitado", name = "campo")
    @JsonProperty("campo")
    private String field;

    @Schema(description = "Descrição da inconsistência", example = "o campo 'valor_solicitado' não dever ser nulo", name = "descricao")
    @JsonProperty("descricao")
    private String description;

}
