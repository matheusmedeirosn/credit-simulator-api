package com.creditsimulator.rest;

import com.creditsimulator.core.LoanSimulationService;
import com.creditsimulator.rest.mapper.RequestToModel;
import com.creditsimulator.rest.mapper.ResponseModelToDTO;
import com.creditsimulator.rest.message.request.LoanRequest;
import com.creditsimulator.rest.message.response.LoanResponse;
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
public class LoanController {

    private final LoanSimulationService loanSimulationService;

    @PostMapping("/simulacao")
    public ResponseEntity<LoanResponse> simulateLoan(@Valid @RequestBody LoanRequest request) {
        LoanResponse response = ResponseModelToDTO.INSTANCE.toResponseModel(
                loanSimulationService.simulateLoan(RequestToModel.INSTANCE.toRequestModel(request))
        );
        return ResponseEntity.ok(response);
    }

}
