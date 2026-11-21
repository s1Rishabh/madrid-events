package com.rishabh.madrid_events.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateEventRequest(
        @NotBlank(message = "title is required")
        @Size(max = 200, message = "title must be at most 200 characters")
        String title,

        String category,
        String venue,

        @NotNull(message = "startTime is required")
        LocalDateTime startTime,

        LocalDateTime endTime,
        Boolean free
) {
}