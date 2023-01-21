package com.schedule.Schedule.V1.dto.contacts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactsRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotNull
    private Long phoneNumber;

}
