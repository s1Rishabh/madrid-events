package com.rishabh.madrid_events.importer;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "import_runs")
public class ImportRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

    @Enumerated(EnumType.STRING)
    private ImportStatus status;

    private int fetched;
    private int created;
    private int updated;
    private int skipped;

    @Column(columnDefinition = "text")
    private String errorMessage;

    protected ImportRun() {
    }

    public static ImportRun success(LocalDateTime startedAt, ImportResult result) {
        ImportRun run = new ImportRun();
        run.startedAt = startedAt;
        run.finishedAt = LocalDateTime.now();
        run.status = ImportStatus.SUCCESS;
        run.fetched = result.fetched();
        run.created = result.created();
        run.updated = result.updated();
        run.skipped = result.skipped();
        return run;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getUpdated() {
        return updated;
    }

    public int getCreated() {
        return created;
    }

    public int getFetched() {
        return fetched;
    }

    public ImportStatus getStatus() {
        return status;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public Long getId() {
        return id;
    }


    public static ImportRun failure(LocalDateTime startedAt, String errorMessage) {
        ImportRun run = new ImportRun();
        run.startedAt = startedAt;
        run.finishedAt = LocalDateTime.now();
        run.status = ImportStatus.FAILED;
        run.errorMessage = errorMessage;
        return run;
    }

}