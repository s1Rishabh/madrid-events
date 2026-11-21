package com.rishabh.madrid_events.event;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String category,
        String venue,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean free
) {
    public static EventResponse from(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getCategory(),
                event.getVenue(),
                event.getStartTime(),
                event.getEndTime(),
                event.isFree()
        );
    }
}