package com.rishabh.madrid_events.importer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MadridFeedResponse(
        @JsonProperty("@graph") List<MadridEvent> events
) {
}