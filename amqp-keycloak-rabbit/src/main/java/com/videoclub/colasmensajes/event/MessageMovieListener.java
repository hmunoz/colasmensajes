package com.videoclub.colasmensajes.event;

import com.videoclub.colasmensajes.config.RabbitMQConfig;
import com.videoclub.colasmensajes.model.Movie;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class MessageMovieListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "movieQueue", durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.EXCHANGE_NAME, type = "topic"),
            key = "Movie.*")
    )
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void handlePeliculaEvent(Event<String, Movie> event) {


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


    @Recover
    public void recover(Exception e, Event<String, Movie> event) {
        event.getData();
    }
}