package com.schedule.Schedule.V1.dto.notes;

import com.schedule.Schedule.V1.enums.NotesColors;
import com.schedule.Schedule.V1.model.TaskNotes;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotesResponse {

    private UUID uuid;

    private String title;

    private String description;

    private LocalDate date;

    private LocalTime time;

    private NotesColors color;

    private List<TaskNotes> taskNotes;
}
