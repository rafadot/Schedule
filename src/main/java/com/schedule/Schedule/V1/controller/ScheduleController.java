package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.schedule.ScheduleResponse;
import com.schedule.Schedule.V1.service.interfaces.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(@RequestParam UUID userUUID){
        return new ResponseEntity<>(scheduleService.create(userUUID) , HttpStatus.CREATED);
    }
}
