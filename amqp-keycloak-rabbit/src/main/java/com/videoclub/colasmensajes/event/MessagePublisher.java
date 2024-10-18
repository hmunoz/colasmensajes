package com.videoclub.colasmensajes.event;

import com.videoclub.colasmensajes.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    private final AmqpTemplate amqpTemplate;

    public MessagePublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public <K, T> void publishEvent(Event<K, T> event) {
        amqpTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, event.getRoutingkey(), event);
    }
}
