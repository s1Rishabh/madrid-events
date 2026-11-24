package com.rishabh.madrid_events.importer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/import")
public class ImportController {

    private final MadridFeedClient feedClient;

    public ImportController(MadridFeedClient feedClient) {
        this.feedClient = feedClient;
    }

    @GetMapping("/preview")
    public FeedPreview preview() {
        List<MadridEvent> events = feedClient.fetchEvents();
        return new FeedPreview(events.size(), events.stream().limit(3).toList());
    }
}