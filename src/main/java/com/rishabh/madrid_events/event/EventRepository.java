package com.rishabh.madrid_events.event;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCategory(String category);

    boolean existsByExternalId(String externalId);

}