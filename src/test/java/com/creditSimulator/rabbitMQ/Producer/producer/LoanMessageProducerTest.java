package com.creditSimulator.rabbitMQ.Producer.producer;

import com.creditsimulator.rabbitMQ.config.RabbitMQProperties;
import com.creditsimulator.rabbitMQ.producer.LoanMessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanMessageProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RabbitMQProperties properties;

    @InjectMocks
    private LoanMessageProducer producer;

    private final Object message = "teste";

    @BeforeEach
    void setUp() {
        when(properties.getName()).thenReturn("testQueue");
    }

    @Test
    void sendToQueue_Success() {
        producer.sendToQueue(message);

        verify(rabbitTemplate, times(1)).convertAndSend("testQueue", message);
    }

    @Test
    void sendToQueue_ExceptionThrown() {
        doThrow(new RuntimeException("Erro no RabbitMQ"))
                .when(rabbitTemplate)
                .convertAndSend(anyString(), any(Object.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> producer.sendToQueue(message));
        assert (exception.getMessage().contains("Erro no RabbitMQ"));

        verify(rabbitTemplate, times(1)).convertAndSend("testQueue", message);
    }
}
