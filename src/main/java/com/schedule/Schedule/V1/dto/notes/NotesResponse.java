package com.schedule.Schedule.V1.dto.notes;

import lombok.*;

import java.time.LocalDateTime;
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

    private LocalDateTime date;

    private String color;
}
