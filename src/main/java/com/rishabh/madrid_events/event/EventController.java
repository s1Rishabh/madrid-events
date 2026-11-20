package com.rishabh.madrid_events.event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<Event> getEvents(@RequestParam(required = false) String category) {
        if (category == null) {
            return eventRepository.findAll();
        }
        return eventRepository.findByCategory(category);
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody CreateEventRequest request) {
        Event event = new Event(
                request.externalId(),
                request.title(),
                request.category(),
                request.venue(),
                request.startTime(),
                request.endTime(),
                request.free()
        );
        return eventRepository.save(event);
    }
}