package com.videoclub.colasmensajes.controller;


import com.videoclub.colasmensajes.event.Event;
import com.videoclub.colasmensajes.event.MessagePublisher;
import com.videoclub.colasmensajes.model.Actor;
import com.videoclub.colasmensajes.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

@Slf4j
@RestController
public class Integracion {


    @Autowired
    private MessagePublisher messagePublisher;

    @GetMapping("/movie")
    public ResponseEntity<Movie> crearMovie() {
        // Lógica para guardar la película en la base de datos
        Movie movie = new Movie("The Godfather", "Francis Ford Coppola", 1972);
        Event<String, Movie> event = new Event<>(Event.Type.CREATE, movie.getUUID(), movie);
        messagePublisher.publishEvent(event);

        return ResponseEntity.ok(movie);
    }


    @GetMapping("/actor")
    public ResponseEntity<Actor> crearActor() {
        // Lógica para guardar la película en la base de datos
        Actor actor = new Actor("Marlon Brando");
        Event<String, Actor> event = new Event<>(Event.Type.CREATE, actor.getUUID(), actor);
        messagePublisher.publishEvent(event);

        return ResponseEntity.ok(actor);
    }
}
