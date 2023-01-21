package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.notes.NotesRequest;
import com.schedule.Schedule.V1.dto.notes.NotesResponse;

import java.util.Map;
import java.util.UUID;

public interface NotesService {
    NotesResponse create(NotesRequest notesRequest, UUID scheduleUUID);

    Map<String, String> delete(UUID uuid);

    NotesResponse update(UUID uuid, NotesRequest notesRequest);
}
