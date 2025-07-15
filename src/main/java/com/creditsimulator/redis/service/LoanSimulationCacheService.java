package com.creditsimulator.redis.service;

import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import com.creditsimulator.redis.exception.RedisOperationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoanSimulationCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String SIMULATION_KEY_PREFIX = "LOAN_SIMULATION_";
    private static final long TTL_MINUTES = 15;


    public void saveSimulationToCache(String simulationId, LoanSimulationResponseModel simulation) {
        String key = SIMULATION_KEY_PREFIX + simulationId;

        try {
            redisTemplate.opsForValue().set(
                    key,
                    simulation,
                    TTL_MINUTES,
                    TimeUnit.MINUTES
            );

            log.info("Simulação [{}] armazenada no cache com sucesso (TTL: {} minutos)", key, TTL_MINUTES);
        } catch (Exception e) {
            log.error("Falha ao armazenar simulação [{}] no cache. Motivo: {}", simulationId, e.getMessage(), e);
        }
    }

    public Optional<LoanSimulationResponseModel> getSimulation(String simulationId) {
        String key = SIMULATION_KEY_PREFIX + simulationId;
        log.debug("Iniciando consulta no cache para chave: {}", key);

        try {
            LoanSimulationResponseModel simulation = (LoanSimulationResponseModel) redisTemplate.opsForValue().get(key);

            if (simulation != null) {
                log.debug("Cache HIT para chave: {}", key);
                return Optional.of(simulation);
            } else {
                log.debug("Cache MISS para chave: {}", key);
                return Optional.empty();
            }

        } catch (Exception e) {
            log.error("Falha ao consultar cache para chave: {} | Erro: {} | Causa: {}", key, e.getClass().getSimpleName(), e.getMessage(), e);
            throw new RedisOperationException("Falha ao recuperar simulação do cache", e);
        }
    }

    public void invalidateSimulation(String simulationId) {
        String key = SIMULATION_KEY_PREFIX + simulationId;
        redisTemplate.delete(key);
    }

}
