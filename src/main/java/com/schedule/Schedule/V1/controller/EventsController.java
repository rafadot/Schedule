package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.events.EventsRequest;
import com.schedule.Schedule.V1.dto.events.EventsResponse;
import com.schedule.Schedule.V1.model.Events;
import com.schedule.Schedule.V1.service.interfaces.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventsService eventsService;

    @PostMapping
    public ResponseEntity<EventsResponse> create(@RequestParam UUID scheduleUUID, @Valid @RequestBody EventsRequest eventsRequest){
        return new ResponseEntity<>(eventsService.create(scheduleUUID,eventsRequest), HttpStatus.CREATED);
    }

    @GetMapping("/addFriend")
    public ResponseEntity<Map<String,String>> addFriendToEvent(@RequestParam UUID eventUUID, @RequestParam UUID usersUUID){
        return new ResponseEntity<>(eventsService.group(eventUUID,usersUUID),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Events>> getAll(@RequestParam UUID scheduleUUID){
        return new ResponseEntity<>(eventsService.getAll(scheduleUUID),HttpStatus.OK);
    }
}
