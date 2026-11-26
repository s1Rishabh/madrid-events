package com.rishabh.madrid_events.importer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/import")
public class ImportController {

    private final MadridFeedClient feedClient;
    private final ImportJob importJob;

    public ImportController(MadridFeedClient feedClient, ImportJob importJob) {
        this.feedClient = feedClient;
        this.importJob = importJob;
    }

    @GetMapping("/preview")
    public FeedPreview preview() {
        List<MadridEvent> events = feedClient.fetchEvents();
        return new FeedPreview(events.size(), events.stream().limit(3).toList());
    }

    @PostMapping("/run")
    public ImportRunResponse run() {
        return importJob.run();
    }

    @GetMapping("/runs")
    public List<ImportRunResponse> runs() {
        return importJob.recentRuns();
    }
}