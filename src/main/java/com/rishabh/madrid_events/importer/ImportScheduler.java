package com.rishabh.madrid_events.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ImportScheduler {

    private static final Logger log = LoggerFactory.getLogger(ImportScheduler.class);

    private final ImportJob importJob;

    public ImportScheduler(ImportJob importJob) {
        this.importJob = importJob;
    }

    @Scheduled(cron = "${madrid.import.cron}", zone = "Europe/Madrid")
    public void nightlyImport() {
        log.info("Scheduled import started");
        try {
            importJob.run();
        } catch (RuntimeException e) {
            log.error("Scheduled import failed", e);
        }
    }
}