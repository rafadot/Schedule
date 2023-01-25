package com.schedule.Schedule.V1.dto.schedule;

import com.schedule.Schedule.V1.model.Contacts;
import com.schedule.Schedule.V1.model.Events;
import com.schedule.Schedule.V1.model.Notes;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse {

    private UUID uuid;

    private String creatorName;

    private List<Contacts> contacts;

    private List<Notes> notes;

    private List<Events> events;
}
