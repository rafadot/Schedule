package com.schedule.Schedule.V1.dto.friends;

import com.schedule.Schedule.V1.enums.Genre;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendsResponse {
    private UUID uuid;

    private String email;

    private String fullName;

    private String nickName;

    private LocalDate birthDate;

    private Genre genre;

    private CommonEvents commonEvents;
}
