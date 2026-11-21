package com.rishabh.madrid_events.event;

public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException(String externalId) {
        super("Event with externalId '" + externalId + "' already exists");
    }
}