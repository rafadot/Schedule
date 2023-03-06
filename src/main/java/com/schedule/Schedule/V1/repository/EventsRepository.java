package com.schedule.Schedule.V1.repository;

import com.schedule.Schedule.V1.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface EventsRepository extends JpaRepository<Events, UUID> {

    @Modifying
    @Query("DELETE from Events e WHERE e.uuid = :eventUuid")
    void deleteEventAndScheduleRelations(@Param("eventUuid") UUID eventUUID);
}
