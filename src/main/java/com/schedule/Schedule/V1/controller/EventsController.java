package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.events.EventsRequest;
import com.schedule.Schedule.V1.dto.events.EventsResponse;
import com.schedule.Schedule.V1.model.Events;
import com.schedule.Schedule.V1.service.interfaces.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @PatchMapping
    public ResponseEntity<EventsResponse> patchEvent(@RequestParam UUID scheduleUUID,
                                                     @RequestParam UUID eventUUID,
                                                     @RequestBody EventsRequest eventsRequest){
        return new ResponseEntity<>(eventsService.patchEvent(scheduleUUID,eventUUID,eventsRequest),HttpStatus.OK);
    }

    @GetMapping("/addFriend")
    public ResponseEntity<Map<String,String>> addFriendToEvent(@RequestParam UUID eventUUID, @RequestParam String friendNickName){
        return new ResponseEntity<>(eventsService.group(eventUUID,friendNickName),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EventsResponse>> getAll(@RequestParam UUID scheduleUUID){
        return new ResponseEntity<>(eventsService.getAll(scheduleUUID),HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<Void> deleteEvent(@RequestParam UUID eventUUID){
        eventsService.deleteEvents(eventUUID);
        return null;
    }
}
