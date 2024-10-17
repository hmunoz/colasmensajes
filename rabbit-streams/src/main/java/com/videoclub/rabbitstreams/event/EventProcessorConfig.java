package com.videoclub.rabbitstreams.event;


import com.videoclub.rabbitstreams.exceptions.EventProcessingException;
import com.videoclub.rabbitstreams.model.Actor;
import com.videoclub.rabbitstreams.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class EventProcessorConfig {

    public static final String EVENT_MOVIE_PROCESSOR = "eventMovieProcessor-out-0";
    public static final String EVENT_ACTOR_PROCESSOR = "eventActorProcessor-out-0";

    @Bean
    public Consumer<Event<String, Movie>> eventMovieProcessor() {
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

    @Bean
    public Consumer<Event<String, Actor>> eventActorProcessor() {
        return event -> {
            log.info("Process message created at {}...", event.getEventCreatedAt());

            switch (event.getEventType()) {

                case CREATE:
                    Actor actor = event.getData();
                    log.info("Create Actor with ID: {}", actor.getUUID());
                    //productService.createProduct(product).block();
                    break;

                case DELETE:
                    String actorId = event.getKey();
                    log.info("Delete actorId with actorId: {}", actorId);
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