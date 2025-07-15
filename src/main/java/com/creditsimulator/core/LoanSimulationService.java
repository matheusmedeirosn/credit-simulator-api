package com.creditsimulator.core;

import com.creditsimulator.domain.model.simulation.LoanSimulationRequestModel;
import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;

public interface LoanSimulationService {

    public LoanSimulationResponseModel simulateLoan(LoanSimulationRequestModel requestModel);

}
