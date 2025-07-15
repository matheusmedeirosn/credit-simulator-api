package com.creditsimulator.core;

import com.creditsimulator.domain.model.adhesion.LoanAdhesionRequestModel;
import com.creditsimulator.domain.model.adhesion.LoanAdhesionResponseModel;

public interface LoanAdhesionService {

    public LoanAdhesionResponseModel simulateLoan(LoanAdhesionRequestModel requestModel);

}
