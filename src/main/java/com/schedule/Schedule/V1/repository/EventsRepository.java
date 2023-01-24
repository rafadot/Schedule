package com.schedule.Schedule.V1.repository;

import com.schedule.Schedule.V1.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventsRepository extends JpaRepository<Events, UUID> {
}
