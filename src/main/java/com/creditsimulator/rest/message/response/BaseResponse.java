package com.creditsimulator.rest.message.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Contempla o retorno da execução.")
public class BaseResponse {

    @Schema(description = "Código identificador de resposta", example = "200", name = "codigo")
    @JsonProperty("codigo")
    private String code;

    @Schema(description = "Mensagem de resposta", example = "Operação realizada com sucesso", name = "mensagem")
    @JsonProperty("mensagem")
    private String description;

}
