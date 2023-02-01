package com.schedule.Schedule.V1.dto.notes.tasks;

import com.schedule.Schedule.V1.enums.TaskNotesStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskNotesRequest {

    @NotBlank
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskNotesStatus status;
}
