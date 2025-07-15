package com.creditsimulator.rest.mapper;

import com.creditsimulator.domain.model.adhesion.LoanAdhesionRequestModel;
import com.creditsimulator.domain.model.simulation.LoanSimulationRequestModel;
import com.creditsimulator.rest.message.request.LoanAdhesionRequestDTO;
import com.creditsimulator.rest.message.request.LoanSimulationRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RequestToModelMapper {

    RequestToModelMapper INSTANCE = Mappers.getMapper(RequestToModelMapper.class);

    LoanSimulationRequestModel toRequestModel(LoanSimulationRequestDTO request);
    LoanAdhesionRequestModel toRequestModel(LoanAdhesionRequestDTO request);

}
