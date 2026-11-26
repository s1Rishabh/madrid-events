package com.rishabh.madrid_events.importer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportRunRepository extends JpaRepository<ImportRun, Long> {

    List<ImportRun> findTop10ByOrderByStartedAtDesc();
}