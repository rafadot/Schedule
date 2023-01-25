package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.events.EventsRequest;
import com.schedule.Schedule.V1.dto.events.EventsResponse;
import com.schedule.Schedule.V1.model.Events;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EventsService {

    EventsResponse create(UUID scheduleUUID, EventsRequest eventsRequest);

    Map<String, String> group(UUID eventUUID, UUID usersUUID);

    List<Events> getAll(UUID scheduleUUID);
}
