package com.rishabh.madrid_events.importer;

import java.time.LocalDateTime;

public record ImportedEvent(
        String externalId,
        String title,
        String category,
        String venue,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean free,
        String description,
        String price,
        String link,
        Double latitude,
        Double longitude
) {
}