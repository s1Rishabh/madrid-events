package com.rishabh.madrid_events.event;

import java.time.LocalDateTime;

public record CreateEventRequest(
        String externalId,
        String title,
        String category,
        String venue,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean free
) {
}