package com.rishabh.madrid_events.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventResponse> getEvents(String category) {
        List<Event> events = (category == null)
                ? eventRepository.findAll()
                : eventRepository.findByCategory(category);

        return events.stream()
                .map(EventResponse::from)
                .toList();
    }

    public EventResponse getEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return EventResponse.from(event);
    }

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {
        if (eventRepository.existsByExternalId(request.externalId())) {
            throw new DuplicateEventException(request.externalId());
        }

        Event event = new Event(
                request.externalId(),
                request.title(),
                request.category(),
                request.venue(),
                request.startTime(),
                request.endTime(),
                Boolean.TRUE.equals(request.free())
        );

        return EventResponse.from(eventRepository.save(event));
    }
}