package com.rishabh.madrid_events.importer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MadridEvent(
        String id,
        String title,
        String description,
        Integer free,
        String price,
        String dtstart,
        String dtend,
        String time,
        String link,
        @JsonProperty("event-location") String eventLocation,
        @JsonProperty("@type") String type,
        Location location
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Location(Double latitude, Double longitude) {
    }
}