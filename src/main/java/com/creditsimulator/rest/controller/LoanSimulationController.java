package com.creditsimulator.rest.controller;

import com.creditsimulator.core.LoanSimulationService;
import com.creditsimulator.domain.exception.LoanSimulationException;
import com.creditsimulator.rest.mapper.RequestToModelMapper;
import com.creditsimulator.rest.mapper.ResponseModelToDtoMapper;
import com.creditsimulator.rest.message.request.LoanSimulationRequestDTO;
import com.creditsimulator.rest.message.response.BaseResponse;
import com.creditsimulator.rest.message.response.LoanAdhesionResponseDTO;
import com.creditsimulator.rest.message.response.LoanSimulationResponseDTO;
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
@Tag(name = "Simulação de empréstimo")
public class LoanSimulationController {

    private final LoanSimulationService simulationService;

    @Operation(
            summary = "Simular adesão a empréstimo",
            description = "Realiza a simulação de condições para adesão a um empréstimo com base nos parâmetros fornecidos"
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
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping("/simulacao")
    public ResponseEntity<LoanSimulationResponseDTO> simulateLoan(@Valid @RequestBody LoanSimulationRequestDTO request) throws LoanSimulationException {
        LoanSimulationResponseDTO response = ResponseModelToDtoMapper.INSTANCE.toResponseModel(
                simulationService.simulateLoan(RequestToModelMapper.INSTANCE.toRequestModel(request))
        );
        return ResponseEntity.ok(response);
    }

}
