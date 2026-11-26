package com.rishabh.madrid_events.importer;

import com.rishabh.madrid_events.event.Event;
import com.rishabh.madrid_events.event.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EventImportService {

    private static final Logger log = LoggerFactory.getLogger(EventImportService.class);

    private final MadridFeedClient feedClient;
    private final MadridEventMapper mapper;
    private final EventRepository eventRepository;

    public EventImportService(MadridFeedClient feedClient, MadridEventMapper mapper,
                              EventRepository eventRepository) {
        this.feedClient = feedClient;
        this.mapper = mapper;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public ImportResult importEvents() {
        long start = System.currentTimeMillis();
        List<MadridEvent> rawEvents = feedClient.fetchEvents();

        Map<String, Event> existing = eventRepository.findAll().stream()
                .collect(Collectors.toMap(Event::getExternalId, Function.identity()));

        List<Event> newEvents = new ArrayList<>();
        Set<String> seenIds = new HashSet<>();
        int updated = 0;
        int skipped = 0;

        for (MadridEvent raw : rawEvents) {
            Optional<ImportedEvent> result = mapper.toImportedEvent(raw);
            if (result.isEmpty() || !seenIds.add(result.get().externalId())) {
                skipped++;
                continue;
            }
            ImportedEvent e = result.get();

            Event event = existing.get(e.externalId());
            if (event == null) {
                event = new Event(e.externalId(), e.title(), e.category(), e.venue(),
                        e.startTime(), e.endTime(), e.free());
                newEvents.add(event);
            } else {
                event.update(e.title(), e.category(), e.venue(),
                        e.startTime(), e.endTime(), e.free());
                updated++;
            }
            event.updateDetails(e.description(), e.price(), e.link(), e.latitude(), e.longitude());
        }

        eventRepository.saveAll(newEvents);

        ImportResult importResult = new ImportResult(rawEvents.size(), newEvents.size(), updated,
                skipped, System.currentTimeMillis() - start);
        log.info("Import finished: {}", importResult);
        return importResult;
    }
}