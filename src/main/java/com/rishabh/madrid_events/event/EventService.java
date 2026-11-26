package com.rishabh.madrid_events.event;

import com.rishabh.madrid_events.common.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public PageResponse<EventResponse> searchEvents(EventFilter filter, Pageable pageable) {
        List<Specification<Event>> specs = new ArrayList<>();

        if (filter.category() != null) {
            specs.add(EventSpecifications.hasCategory(filter.category()));
        }
        if (filter.free() != null) {
            specs.add(EventSpecifications.isFree(filter.free()));
        }
        if (filter.from() != null) {
            specs.add(EventSpecifications.activeOnOrAfter(filter.from().atStartOfDay()));
        }
        if (filter.to() != null) {
            specs.add(EventSpecifications.startsBefore(filter.to().plusDays(1).atStartOfDay()));
        }
        if (filter.q() != null && !filter.q().isBlank()) {
            specs.add(EventSpecifications.titleContains(filter.q().trim()));
        }

        Page<EventResponse> page = eventRepository
                .findAll(Specification.allOf(specs), pageable)
                .map(EventResponse::from);

        return PageResponse.from(page);
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

    @Transactional
    public EventResponse updateEvent(Long id, UpdateEventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        event.update(
                request.title(),
                request.category(),
                request.venue(),
                request.startTime(),
                request.endTime(),
                Boolean.TRUE.equals(request.free())
        );

        return EventResponse.from(event);
    }

    @Transactional
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }
        eventRepository.deleteById(id);
    }
}