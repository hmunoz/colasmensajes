package com.videoclub.rabbitstreams.controller;

import com.videoclub.rabbitstreams.event.Event;
import com.videoclub.rabbitstreams.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;


import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Integracion {

    public static final String EVENT_MOVIES_PROCESSOR = "eventMoviesProcessor-out-0";

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/")
    public void sendCreateMovieEvent() {
        Movie movie = new Movie("The Godfather", "Francis Ford Coppola", 1972);
        sendMessage(EVENT_MOVIES_PROCESSOR, new Event(Event.Type.CREATE, movie.getUUID(), movie));
    }

    private void sendMessage(String bindingName, Event event) {
        log.debug("Sending a {} message to {}", event.getEventType(), bindingName);
        Message message = MessageBuilder.withPayload(event)
                .setHeader("partitionKey", event.getKey())
                .setHeader("routingKey", event.getRoutingkey())
                .build();
        streamBridge.send(bindingName, message);
    }
}
