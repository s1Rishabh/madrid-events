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

    public void update(String title, String category, String venue,
                       LocalDateTime startTime, LocalDateTime endTime, boolean free) {
        this.title = title;
        this.category = category;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.free = free;
    }

    public void updateDetails(String description, String price, String link,
                              Double latitude, Double longitude) {
        this.description = description;
        this.price = price;
        this.link = link;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
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

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String price;

    @Column(columnDefinition = "text")
    private String link;

    private Double latitude;
    private Double longitude;
}