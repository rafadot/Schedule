package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.contacts.ContactsRequest;
import com.schedule.Schedule.V1.dto.contacts.ContactsResponse;
import com.schedule.Schedule.V1.model.Contacts;
import com.schedule.Schedule.V1.service.interfaces.ContactsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactsController {

    private final ContactsService contactsService;



    @PostMapping
    public ResponseEntity<ContactsResponse> create(@Valid @RequestBody ContactsRequest contactsRequest, @RequestParam UUID scheduleUUID){
        return new ResponseEntity<>(contactsService.create(contactsRequest , scheduleUUID) , HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteContact(@RequestParam UUID uuid){
        return new ResponseEntity<>(contactsService.deleteContact(uuid) ,HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ContactsResponse> updates(@RequestParam UUID uuid, @RequestBody ContactsRequest updates){
        return new ResponseEntity<>(contactsService.update(uuid ,updates) , HttpStatus.OK);
    }

}
