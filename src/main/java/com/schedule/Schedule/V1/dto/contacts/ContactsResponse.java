package com.schedule.Schedule.V1.dto.contacts;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactsResponse {

    private UUID uuid;

    private String name;

    private String nickname;

    private Long phoneNumber;
}
