package com.rishabh.madrid_events.importer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/import")
public class ImportController {

    private final MadridFeedClient feedClient;
    private final EventImportService importService;

    public ImportController(MadridFeedClient feedClient, EventImportService importService) {
        this.feedClient = feedClient;
        this.importService = importService;
    }

    @GetMapping("/preview")
    public FeedPreview preview() {
        List<MadridEvent> events = feedClient.fetchEvents();
        return new FeedPreview(events.size(), events.stream().limit(3).toList());
    }

    @PostMapping("/run")
    public ImportResult run() {
        return importService.importEvents();
    }
}