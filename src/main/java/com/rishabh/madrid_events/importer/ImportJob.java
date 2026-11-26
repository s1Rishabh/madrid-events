package com.rishabh.madrid_events.importer;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ImportJob {

    private final EventImportService importService;
    private final ImportRunRepository runRepository;

    public ImportJob(EventImportService importService, ImportRunRepository runRepository) {
        this.importService = importService;
        this.runRepository = runRepository;
    }

    public ImportRunResponse run() {
        LocalDateTime startedAt = LocalDateTime.now();
        try {
            ImportResult result = importService.importEvents();
            ImportRun run = runRepository.save(ImportRun.success(startedAt, result));
            return ImportRunResponse.from(run);
        } catch (RuntimeException e) {
            String message = e.getMessage();
            if (e.getCause() != null) {
                message += ": " + e.getCause().getMessage();
            }
            runRepository.save(ImportRun.failure(startedAt, message));
            throw e;
        }
    }

    public List<ImportRunResponse> recentRuns() {
        return runRepository.findTop10ByOrderByStartedAtDesc().stream()
                .map(ImportRunResponse::from)
                .toList();
    }
}