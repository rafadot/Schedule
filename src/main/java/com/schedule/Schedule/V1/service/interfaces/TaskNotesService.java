package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.notes.tasks.TaskNotesRequest;
import com.schedule.Schedule.V1.dto.notes.tasks.TaskNotesResponse;
import com.schedule.Schedule.V1.model.TaskNotes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TaskNotesService {

    TaskNotesResponse create(UUID noteUUID, TaskNotesRequest taskNotesRequest);

    Map<String, String> deleteTask(UUID taskUUID);

    List<TaskNotes> manyPatchTask(List<TaskNotes> taskNotesList);
}
