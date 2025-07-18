package com.creditsimulator.core.impl;

import com.creditsimulator.core.LoanAdhesionService;
import com.creditsimulator.domain.exception.SimulationNotFoundException;
import com.creditsimulator.domain.mapper.AdhesionRequestToResponseMapper;
import com.creditsimulator.domain.model.adhesion.LoanAdhesionRequestModel;
import com.creditsimulator.domain.model.adhesion.LoanAdhesionResponseModel;
import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import com.creditsimulator.rabbitMQ.producer.LoanMessageProducer;
import com.creditsimulator.redis.service.LoanSimulationCacheService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanAdhesionServiceImpl implements LoanAdhesionService {

    private final LoanSimulationCacheService cacheService;
    private final LoanMessageProducer messageProducer;

    @SneakyThrows
    @Override
    public LoanAdhesionResponseModel simulateLoan(LoanAdhesionRequestModel requestModel) {
        LoanSimulationResponseModel simulation = cacheService.getSimulation(requestModel.getSimulationId())
                .orElseThrow(() -> new SimulationNotFoundException("Simulação de ID " + requestModel.getSimulationId() + "não encontrada ou expirada"));
        requestModel.fromSimulation(simulation);
        messageProducer.sendToQueue(requestModel);
        cacheService.invalidateSimulation(requestModel.getSimulationId());
        return AdhesionRequestToResponseMapper.INSTANCE.toRequestModel(requestModel);
    }
}
