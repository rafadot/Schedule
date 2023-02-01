package com.schedule.Schedule.V1.dto.notes.tasks;

import com.schedule.Schedule.V1.enums.TaskNotesStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskNotesResponse {

    private UUID uuid;

    private String description;

    private TaskNotesStatus status;
}
