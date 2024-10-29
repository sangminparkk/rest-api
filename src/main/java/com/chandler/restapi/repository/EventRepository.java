package com.chandler.restapi.repository;

import com.chandler.restapi.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
