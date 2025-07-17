package com.creditsimulator.rest.controller;

import com.creditsimulator.core.LoanAdhesionService;
import com.creditsimulator.domain.model.adhesion.LoanAdhesionResponseModel;
import com.creditsimulator.rest.mapper.RequestToModelMapper;
import com.creditsimulator.rest.mapper.ResponseModelToDtoMapper;
import com.creditsimulator.rest.message.request.LoanAdhesionRequestDTO;
import com.creditsimulator.rest.message.response.BaseResponse;
import com.creditsimulator.rest.message.response.LoanAdhesionResponseDTO;
import com.creditsimulator.rest.message.response.exception.BadRequestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emprestimo")
@Tag(name = "Adesão de empréstimo", description = "Necessário de uma simulação prévia")
public class LoanAdhesionController {

    private final LoanAdhesionService adhesionService;

    @Operation(
            summary = "Aderir a empréstimo já simulado",
            description = "Realiza a solicitação de adesão a um empréstimo já simulado com base nos parâmetros fornecidos"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Simulação realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = LoanAdhesionResponseDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida",
                    content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(
                    responseCode = "204",
                    description = "Simulação de empréstimo não encontrada."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping("/adesao")
    public ResponseEntity<LoanAdhesionResponseDTO> adhesionLoan(@Valid @RequestBody LoanAdhesionRequestDTO requestDTO) {
        LoanAdhesionResponseModel responseModel = adhesionService.simulateLoan(RequestToModelMapper.INSTANCE.toRequestModel(requestDTO));
        return ResponseEntity.ok(ResponseModelToDtoMapper.INSTANCE.toResponseModel(responseModel));
    }

}
