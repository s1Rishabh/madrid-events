package com.rishabh.madrid_events.importer;

public record ImportResult(int fetched, int created, int updated, int skipped, long durationMs) {
}