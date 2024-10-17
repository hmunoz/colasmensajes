package com.videoclub.rabbitstreams.controller;

import com.videoclub.rabbitstreams.event.Event;
import com.videoclub.rabbitstreams.model.Actor;
import com.videoclub.rabbitstreams.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;


import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.videoclub.rabbitstreams.event.EventProcessorConfig.EVENT_ACTOR_PROCESSOR;
import static com.videoclub.rabbitstreams.event.EventProcessorConfig.EVENT_MOVIE_PROCESSOR;

@Slf4j
@RestController
public class Integracion {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/movie")
    public void sendCreateMovieEvent() {
        Movie movie = new Movie("The Godfather", "Francis Ford Coppola", 1972);
        sendMessage(EVENT_MOVIE_PROCESSOR, new Event(Event.Type.CREATE, movie.getUUID(), movie));
    }

    @GetMapping("/actor")
    public void sendCreateActorEvent() {
        Actor actor = new Actor("Marlon Brando");
        sendMessage(EVENT_ACTOR_PROCESSOR, new Event(Event.Type.CREATE, actor.getUUID(), actor));
    }

    private void sendMessage(String bindingName, Event event) {
        log.debug("Sending a {} message to {}", event.getEventType(), bindingName);
        Message<Event> message = MessageBuilder.withPayload(event)
                .setHeader("partitionKey", event.getKey())
                .setHeader("routingKey", event.getRoutingkey())
                .build();
        streamBridge.send(bindingName, message);
    }
}
