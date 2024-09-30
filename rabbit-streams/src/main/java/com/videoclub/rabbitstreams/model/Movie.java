package com.videoclub.rabbitstreams.model;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Movie {

    private String UUID;
    private String title;
    private String director;
    private int year;

    public Movie() {
    }

    public Movie(String title, String director, int year) {
        this.UUID = java.util.UUID.randomUUID().toString();
        this.title = title;
        this.director = director;
        this.year = year;
    }
}
