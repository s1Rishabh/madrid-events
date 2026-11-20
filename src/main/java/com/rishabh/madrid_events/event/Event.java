package com.rishabh.madrid_events.event;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true)
    private String externalId;

    @Column(nullable = false)
    private String title;

    private String category;
    private String venue;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean free;

    protected Event() {
    }

    public Event(String externalId, String title, String category, String venue,
                 LocalDateTime startTime, LocalDateTime endTime, boolean free) {
        this.externalId = externalId;
        this.title = title;
        this.category = category;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.free = free;
    }

    public Long getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getVenue() {
        return venue;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isFree() {
        return free;
    }
}