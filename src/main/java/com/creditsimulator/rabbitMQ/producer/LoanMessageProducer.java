package com.creditsimulator.rabbitMQ.producer;

import com.creditsimulator.rabbitMQ.config.RabbitMQProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class LoanMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties properties;

    public void sendToQueue(Object message) {
        rabbitTemplate.convertAndSend(properties.getName(), message);
        log.info("Mensagem enviada para a fila: " + message);
    }

}
