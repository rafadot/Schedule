package com.schedule.Schedule.V1.dto.users;

import com.schedule.Schedule.V1.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String fullName;

    @NotBlank
    private String nickName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Genre genre;
}
