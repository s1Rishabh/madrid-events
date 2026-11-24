package com.rishabh.madrid_events.event;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public final class EventSpecifications {

    private EventSpecifications() {
    }

    public static Specification<Event> hasCategory(String category) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.<String>get("category")), category.toLowerCase());
    }

    public static Specification<Event> isFree(boolean free) {
        return (root, query, cb) -> cb.equal(root.get("free"), free);
    }

    public static Specification<Event> startsOnOrAfter(LocalDateTime from) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.<LocalDateTime>get("startTime"), from);
    }

    public static Specification<Event> startsBefore(LocalDateTime to) {
        return (root, query, cb) ->
                cb.lessThan(root.<LocalDateTime>get("startTime"), to);
    }

    public static Specification<Event> titleContains(String text) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.<String>get("title")), "%" + text.toLowerCase() + "%");
    }
}