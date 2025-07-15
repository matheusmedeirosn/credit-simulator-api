package com.creditsimulator.rest.mapper;

import com.creditsimulator.domain.model.LoanSimulationRequestModel;
import com.creditsimulator.rest.message.request.LoanRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RequestToModel {

    RequestToModel INSTANCE = Mappers.getMapper(RequestToModel.class);

    LoanSimulationRequestModel toRequestModel(LoanRequest request);

}
