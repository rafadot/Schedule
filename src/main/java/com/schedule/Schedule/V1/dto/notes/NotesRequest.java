package com.schedule.Schedule.V1.dto.notes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesRequest {

    @NotBlank
    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    private String color;
}
