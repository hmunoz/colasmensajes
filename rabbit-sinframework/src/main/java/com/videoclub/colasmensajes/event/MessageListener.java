package com.videoclub.colasmensajes.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.videoclub.colasmensajes.model.Movie;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageListener {

    // Event
    @Value("${rabbitmq.event.exchange.name}")
    private String eventExchange;

    // KeyCloak
    @Value("${rabbitmq.event.movie.queue.name}")
    private String queueMovie;



    @Value("${rabbitmq.event.movie.routing.key}")
    private String routingKeyMovie;

    private final Connection connection;

    private final ObjectMapper objectMapper;

    public MessageListener(Connection connection, ObjectMapper objectMapper) {
        this.connection = connection;
        this.objectMapper = new ObjectMapper();
    }


    @PostConstruct
    public void startConsumerMovie() {
        try {
            Channel channel = connection.createChannel();

            // Declarar el exchange (tipo puede ser "direct", "topic", "fanout", o "headers")
            channel.exchangeDeclare(eventExchange, "topic", true);

            // Declarar la cola
            channel.queueDeclare(queueMovie, false, false, false, null);

            // Enlazar la cola con el exchange usando una routing key
            channel.queueBind(queueMovie, eventExchange, routingKeyMovie);

            System.out.println("Esperando mensajes...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Mensaje recibido: " + message);

                Event<String, Movie> eventmovie = objectMapper.readValue(message, Event.class);
            };

            // Iniciar consumo de la cola
            channel.basicConsume(queueMovie, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}