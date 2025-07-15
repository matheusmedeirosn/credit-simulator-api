package com.creditsimulator.rest.mapper;

import com.creditsimulator.domain.model.LoanSimulationResponseModel;
import com.creditsimulator.rest.message.response.LoanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResponseModelToDTO {

    ResponseModelToDTO INSTANCE = Mappers.getMapper(ResponseModelToDTO.class);

    LoanResponse toResponseModel(LoanSimulationResponseModel request);

}
