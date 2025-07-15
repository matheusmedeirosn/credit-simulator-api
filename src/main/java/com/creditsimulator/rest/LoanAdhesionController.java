package com.creditsimulator.rest;

import com.creditsimulator.core.LoanAdhesionService;
import com.creditsimulator.domain.model.adhesion.LoanAdhesionResponseModel;
import com.creditsimulator.rest.mapper.RequestToModelMapper;
import com.creditsimulator.rest.mapper.ResponseModelToDtoMapper;
import com.creditsimulator.rest.message.request.LoanAdhesionRequestDTO;
import com.creditsimulator.rest.message.response.LoanAdhesionResponseDTO;
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
public class LoanAdhesionController {

    private final LoanAdhesionService adhesionService;

    @PostMapping("/adesao")
    public ResponseEntity<LoanAdhesionResponseDTO> adhesionLoan(@Valid @RequestBody LoanAdhesionRequestDTO requestDTO) {
        LoanAdhesionResponseModel responseModel = adhesionService.simulateLoan(RequestToModelMapper.INSTANCE.toRequestModel(requestDTO));
        return ResponseEntity.ok(ResponseModelToDtoMapper.INSTANCE.toResponseModel(responseModel));
    }

}
