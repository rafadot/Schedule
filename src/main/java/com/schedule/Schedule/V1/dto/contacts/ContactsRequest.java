package com.schedule.Schedule.V1.dto.contacts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactsRequest {

    private String name;

    private String nickname;

    private Long phoneNumber;

}
