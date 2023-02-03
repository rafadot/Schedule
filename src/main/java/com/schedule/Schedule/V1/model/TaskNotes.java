package com.schedule.Schedule.V1.model;

import com.schedule.Schedule.V1.enums.TaskNotesStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskNotes {
    private static final long serialVersionUid = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskNotesStatus status;

}
