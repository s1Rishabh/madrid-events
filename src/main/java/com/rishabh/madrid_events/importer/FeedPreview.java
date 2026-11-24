package com.rishabh.madrid_events.importer;

import java.util.List;

public record FeedPreview(int totalEvents, List<MadridEvent> sample) {
}