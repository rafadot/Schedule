package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.schedule.ScheduleResponse;
import com.schedule.Schedule.V1.service.interfaces.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


}
