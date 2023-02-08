package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.schedule.ScheduleResponse;

import java.util.UUID;

public interface ScheduleService {

//    ScheduleResponse create(UUID usersUUID);

    ScheduleResponse getByScheduleId(UUID scheduleId);

//    ScheduleResponse getByUserId(UUID userId);
}
