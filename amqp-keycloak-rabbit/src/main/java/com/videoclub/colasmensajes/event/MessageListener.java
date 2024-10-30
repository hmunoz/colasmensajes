package com.videoclub.colasmensajes.event;

import com.videoclub.colasmensajes.model.Actor;
import com.videoclub.colasmensajes.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "${rabbitmq.event.movie.queue.name}" ,
                    durable = "true"
            ),
            exchange = @Exchange(value = "${rabbitmq.event.exchange.name}" , type = "topic"),
            key = "${rabbitmq.event.movie.routing.key}"
    ))
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void handleMovieEvent(Event<String, Movie> event) {


        switch (event.getEventType()) {
            case CREATE:
                // Lógica para crear una película
                throw new RuntimeException("Error al procesar el mensaje");
            case DELETE:
                // Lógica para eliminar una película
                break;
            default:
                // Manejo de otros tipos de eventos
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "${rabbitmq.event.actor.queue.name}" ,
                    durable = "true"
            ),
            exchange = @Exchange(value = "${rabbitmq.event.exchange.name}" , type = "topic"),
            key = "${rabbitmq.event.actor.routing.key}"
    ))
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void handleActorEvent(Event<String, Actor> event) {

        switch (event.getEventType()) {
            case CREATE:
                // Lógica para crear una película
                //throw new RuntimeException("Error al procesar el mensaje");
                //repositopry.save(event.getData());
                break;
            case DELETE:
                // Lógica para eliminar una película
                break;
            default:
                // Manejo de otros tipos de eventos
        }
    }

    @Recover
    public void recover(Exception e, Event<String, Movie> event) {
      log.info(event.getData().toString());
    }
}