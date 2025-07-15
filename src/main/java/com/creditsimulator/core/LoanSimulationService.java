package com.creditsimulator.core;

import com.creditsimulator.domain.model.LoanSimulationRequestModel;
import com.creditsimulator.domain.model.LoanSimulationResponseModel;

public interface LoanSimulationService {

    public LoanSimulationResponseModel simulateLoan(LoanSimulationRequestModel request);

}
