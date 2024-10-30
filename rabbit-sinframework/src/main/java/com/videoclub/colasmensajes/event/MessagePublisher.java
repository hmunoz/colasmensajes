package com.videoclub.colasmensajes.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    // Event
    @Value("${rabbitmq.event.exchange.name}")
    private String eventExchange;

    private final Connection connection;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessagePublisher(Connection connection) {
        this.connection = connection;
        this.objectMapper = new ObjectMapper();
    }

    public <K, T> void publishEvent(Event<K, T> event) {
        try (Channel channel = connection.createChannel()) {
            // Convertir el DTO a JSON
            String message = objectMapper.writeValueAsString(event);

            // Enviar el mensaje al exchange con la routing key
            channel.basicPublish(eventExchange, event.getRoutingkey(), null, message.getBytes());
            System.out.println("Mensaje enviado: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}
