package com.creditsimulator.domain.mapper;

import com.creditsimulator.domain.model.adhesion.LoanAdhesionRequestModel;
import com.creditsimulator.domain.model.adhesion.LoanAdhesionResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdhesionRequestToResponseMapper {

    AdhesionRequestToResponseMapper INSTANCE = Mappers.getMapper(AdhesionRequestToResponseMapper.class);

    @Mapping(target = "contractDate", expression = "java(java.time.LocalDate.now(java.time.ZoneId.of(\"America/Sao_Paulo\")))")
    LoanAdhesionResponseModel toRequestModel(LoanAdhesionRequestModel request);


}
