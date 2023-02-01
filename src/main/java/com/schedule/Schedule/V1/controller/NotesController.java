package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.notes.NotesRequest;
import com.schedule.Schedule.V1.dto.notes.NotesResponse;
import com.schedule.Schedule.V1.dto.notes.tasks.TaskNotesRequest;
import com.schedule.Schedule.V1.dto.notes.tasks.TaskNotesResponse;
import com.schedule.Schedule.V1.model.Notes;
import com.schedule.Schedule.V1.model.TaskNotes;
import com.schedule.Schedule.V1.service.interfaces.NotesService;
import com.schedule.Schedule.V1.service.interfaces.TaskNotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {

    private final NotesService notesService;
    private final TaskNotesService taskNotesService;

    @PostMapping
    public ResponseEntity<NotesResponse> create(@Valid @RequestBody NotesRequest notesRequest, @RequestParam UUID scheduleUUID){
        return new ResponseEntity<>(notesService.create(notesRequest,scheduleUUID) , HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<NotesResponse> update(@RequestParam UUID uuid, @RequestBody NotesRequest notesRequest){
        return new ResponseEntity<>(notesService.update(uuid,notesRequest) , HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(@RequestParam UUID uuid){
        return new ResponseEntity<>(notesService.delete(uuid) , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Notes>> getAll(@RequestParam UUID scheduleUUID){
        return new ResponseEntity<>(notesService.getAll(scheduleUUID),HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<NotesResponse> getById(@RequestParam UUID uuid){
        return new ResponseEntity<>(notesService.getById(uuid),HttpStatus.OK);
    }

    @PostMapping("/taskNotes")
    public ResponseEntity<TaskNotesResponse> create(
            @RequestParam UUID noteUUID,
            @RequestBody TaskNotesRequest taskNotesRequest){
        return new ResponseEntity<>(taskNotesService.create(noteUUID,taskNotesRequest),HttpStatus.CREATED);
    }

    @DeleteMapping("/taskNotes")
    public ResponseEntity<Map<String,String>> deleteTask(@RequestParam UUID taskUUID){
        return new ResponseEntity<>(taskNotesService.deleteTask(taskUUID),HttpStatus.OK);
    }

    @PutMapping("/taskNotes")
    public ResponseEntity<TaskNotesResponse> putTaskNotes(@RequestBody TaskNotes taskNotes){
        return new ResponseEntity<>(taskNotesService.putTaskNotes(taskNotes),HttpStatus.OK);
    }
}
