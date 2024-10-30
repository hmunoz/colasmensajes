package com.videoclub.colasmensajes.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Actor {
    private String UUID;
    private String name;

    public Actor() {
    }

    public Actor(String name) {
        this.UUID = java.util.UUID.randomUUID().toString();
        this.name = name;
    }
}
