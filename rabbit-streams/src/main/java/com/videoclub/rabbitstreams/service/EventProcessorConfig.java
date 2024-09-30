package com.videoclub.rabbitstreams.service;


import com.videoclub.rabbitstreams.model.Movie;
import com.videoclub.rabbitstreams.event.Event;
import com.videoclub.rabbitstreams.exceptions.EventProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class EventProcessorConfig {

    @Bean
    public Consumer<Event<String, Movie>> eventMoviesProcessor() {
        return event -> {
            log.info("Process message created at {}...", event.getEventCreatedAt());

            switch (event.getEventType()) {

                case CREATE:
                    Movie movie = event.getData();
                    log.info("Create product with ID: {}", movie.getUUID());
                    //productService.createProduct(product).block();
                    break;

                case DELETE:
                    String movieId = event.getKey();
                    log.info("Delete product with MovieID: {}", movieId);
                    //productService.deleteProduct(productId).block();
                    break;

                default:
                    String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected a CREATE or DELETE event";
                    log.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
            }

            log.info("Message processing done!");

        };
    }


}