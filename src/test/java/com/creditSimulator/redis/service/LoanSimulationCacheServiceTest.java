package com.creditSimulator.redis.service;

import com.creditsimulator.domain.model.simulation.LoanSimulationResponseModel;
import com.creditsimulator.redis.exception.RedisOperationException;
import com.creditsimulator.redis.service.LoanSimulationCacheService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanSimulationCacheServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LoanSimulationCacheService loanSimulationCacheService;

    private final String simulationId = "12345";
    private final String cacheKey = "LOAN_SIMULATION_12345";
    private LoanSimulationResponseModel simulation;

    @BeforeEach
    void setUp() {
        simulation = new LoanSimulationResponseModel();
        simulation.setE2e(simulationId);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void saveSimulationToCache_ShouldSaveWithSuccess() {
        loanSimulationCacheService.saveSimulationToCache(simulationId, simulation);

        verify(valueOperations).set(
                eq(cacheKey),
                eq(simulation),
                eq(15L),
                eq(TimeUnit.MINUTES)
        );
    }

    @Test
    void saveSimulationToCache_ShouldHandleException() {
        doThrow(new RuntimeException("Redis error")).when(valueOperations)
                .set(anyString(), any(), anyLong(), any(TimeUnit.class));

        loanSimulationCacheService.saveSimulationToCache(simulationId, simulation);

        verify(valueOperations).set(
                eq(cacheKey),
                eq(simulation),
                eq(15L),
                eq(TimeUnit.MINUTES)
        );
    }

    @Test
    void getSimulation_WhenKeyExists_ShouldReturnSimulation() {
        when(valueOperations.get(cacheKey)).thenReturn(simulation);

        Optional<LoanSimulationResponseModel> result = loanSimulationCacheService.getSimulation(simulationId);

        assertTrue(result.isPresent());
        assertEquals(simulation, result.get());
        verify(valueOperations).get(cacheKey);
    }

    @Test
    void getSimulation_WhenKeyNotExists_ShouldReturnEmpty() {
        when(valueOperations.get(cacheKey)).thenReturn(null);

        Optional<LoanSimulationResponseModel> result = loanSimulationCacheService.getSimulation(simulationId);

        assertFalse(result.isPresent());
        verify(valueOperations).get(cacheKey);
    }

    @Test
    void getSimulation_WhenRedisFails_ShouldThrowRedisOperationException() {
        when(valueOperations.get(cacheKey)).thenThrow(new RuntimeException("Connection failed"));

        assertThrows(RedisOperationException.class, () -> {
            loanSimulationCacheService.getSimulation(simulationId);
        });

        verify(valueOperations).get(cacheKey);
    }

    @Test
    void invalidateSimulation_ShouldDeleteKey() {
        loanSimulationCacheService.invalidateSimulation(simulationId);

        verify(redisTemplate).delete(cacheKey);
    }

    @Test
    void invalidateSimulation_WhenKeyNotExists_ShouldNotThrowException() {
        doThrow(new RuntimeException("Key not found")).when(redisTemplate).delete(cacheKey);

        loanSimulationCacheService.invalidateSimulation(simulationId);

        verify(redisTemplate).delete(cacheKey);
    }
}