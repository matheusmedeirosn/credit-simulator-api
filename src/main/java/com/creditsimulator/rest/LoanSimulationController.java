package com.creditsimulator.rest;

import com.creditsimulator.core.LoanSimulationService;
import com.creditsimulator.rest.mapper.RequestToModelMapper;
import com.creditsimulator.rest.mapper.ResponseModelToDtoMapper;
import com.creditsimulator.rest.message.request.LoanSimulationRequestDTO;
import com.creditsimulator.rest.message.response.LoanSimulationResponseDTO;
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
public class LoanSimulationController {

    private final LoanSimulationService simulationService;

    @PostMapping("/simulacao")
    public ResponseEntity<LoanSimulationResponseDTO> simulateLoan(@Valid @RequestBody LoanSimulationRequestDTO request) {
        LoanSimulationResponseDTO response = ResponseModelToDtoMapper.INSTANCE.toResponseModel(
                simulationService.simulateLoan(RequestToModelMapper.INSTANCE.toRequestModel(request))
        );
        return ResponseEntity.ok(response);
    }

}
