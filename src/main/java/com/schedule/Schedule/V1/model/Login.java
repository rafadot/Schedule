package com.schedule.Schedule.V1.model;

import com.schedule.Schedule.V1.enums.LoginType;
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
public class Login {

    @NotBlank
    private String emailOrNickName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoginType type;

    @NotBlank
    private String password;
}
