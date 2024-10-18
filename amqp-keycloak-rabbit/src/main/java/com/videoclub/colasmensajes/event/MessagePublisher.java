package com.videoclub.colasmensajes.event;

import com.videoclub.colasmensajes.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    // Event
    @Value("${rabbitmq.event.exchange.name}")
    private String eventExchange;

    private final AmqpTemplate amqpTemplate;

    public MessagePublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public <K, T> void publishEvent(Event<K, T> event) {
        amqpTemplate.convertAndSend(eventExchange, event.getRoutingkey(), event);
    }
}
