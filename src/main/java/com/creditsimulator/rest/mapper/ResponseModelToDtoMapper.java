package com.creditsimulator.rest.mapper;

import com.creditsimulator.domain.model.adhesion.LoanAdhesionResponseModel;
import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import com.creditsimulator.rest.message.response.LoanAdhesionResponseDTO;
import com.creditsimulator.rest.message.response.LoanSimulationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResponseModelToDtoMapper {

    ResponseModelToDtoMapper INSTANCE = Mappers.getMapper(ResponseModelToDtoMapper.class);

    LoanSimulationResponseDTO toResponseModel(LoanSimulationResponseModel request);

    LoanAdhesionResponseDTO toResponseModel(LoanAdhesionResponseModel request);

}
