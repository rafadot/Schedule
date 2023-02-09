package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.notes.NotesRequest;
import com.schedule.Schedule.V1.dto.notes.NotesResponse;
import com.schedule.Schedule.V1.model.Notes;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface NotesService {
    NotesResponse create(NotesRequest notesRequest, UUID scheduleUUID);

    Map<String, String> delete(UUID uuid);

    NotesResponse update(UUID uuid, NotesRequest notesRequest);

    List<NotesResponse> getAll(UUID scheduleUUID);

    NotesResponse getById(UUID uuid);
}
