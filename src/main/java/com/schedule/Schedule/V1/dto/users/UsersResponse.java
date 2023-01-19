package com.schedule.Schedule.V1.dto.users;

import com.schedule.Schedule.V1.enums.Genre;
import com.schedule.Schedule.V1.model.Schedule;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersResponse {

    private UUID uuid;

    private String email;

    private String fullName;

    private LocalDate birthDate;

    private Genre genre;

    private Schedule schedule;
}
