package com.rishabh.madrid_events.event;

import java.time.LocalDate;

public record EventFilter(
        String category,
        Boolean free,
        LocalDate from,
        LocalDate to,
        String q
) {
}