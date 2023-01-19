package com.schedule.Schedule.V1.dto.notes;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotesResponse {

    private String title;

    private String description;

    private LocalDateTime date;
}
