package com.schedule.Schedule.V1.dto.friends;

import com.schedule.Schedule.V1.model.Events;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonEvents {
    private Long totalCommonEvents;
    List<Events> events = new ArrayList<>();

}
