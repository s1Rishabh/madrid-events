package com.rishabh.madrid_events.importer;

import java.time.LocalDateTime;

public record ImportRunResponse(
        Long id,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        ImportStatus status,
        int fetched,
        int created,
        int updated,
        int skipped,
        String errorMessage
) {
    public static ImportRunResponse from(ImportRun run) {
        return new ImportRunResponse(
                run.getId(),
                run.getStartedAt(),
                run.getFinishedAt(),
                run.getStatus(),
                run.getFetched(),
                run.getCreated(),
                run.getUpdated(),
                run.getSkipped(),
                run.getErrorMessage()
        );
    }
}