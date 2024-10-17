package com.videoclub.rabbitstreams.event;

import static java.time.ZonedDateTime.now;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
public class Event<K, T> {

    public enum Type {
        CREATE,
        DELETE
    }

    private final Type eventType;
    private final K key;
    private final T data;
    private final ZonedDateTime eventCreatedAt;

    public Event() {
        this.eventType = null;
        this.key = null;
        this.data = null;
        this.eventCreatedAt = null;
    }

    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = now();
    }

    @JsonIgnore
    public String getRoutingkey() {return  this.getData().getClass().getSimpleName() + "." + this.getEventType();}

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    public ZonedDateTime getEventCreatedAt() {
        return eventCreatedAt;
    }
}