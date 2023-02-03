package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.notes.tasks.TaskNotesRequest;
import com.schedule.Schedule.V1.dto.notes.tasks.TaskNotesResponse;
import com.schedule.Schedule.V1.model.Notes;
import com.schedule.Schedule.V1.model.Schedule;
import com.schedule.Schedule.V1.model.TaskNotes;
import com.schedule.Schedule.V1.repository.NotesRepository;
import com.schedule.Schedule.V1.repository.TaskNotesRepository;
import com.schedule.Schedule.V1.service.interfaces.TaskNotesService;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskNotesServiceImpl implements TaskNotesService {

    private final TaskNotesRepository taskNotesRepository;
    private final NotesRepository notesRepository;

    @Override
    public TaskNotesResponse create(UUID noteUUID, TaskNotesRequest taskNotesRequest) {
        Optional<Notes> notes = notesRepository.findById(noteUUID);

        if(!notes.isPresent())
            throw new BadRequestException("Id da nota não encontrado");

        TaskNotes taskNotes = new TaskNotes();
        BeanUtils.copyProperties(taskNotesRequest,taskNotes);
        taskNotesRepository.save(taskNotes);

        notes.get().getTaskNotes().add(taskNotes);
        notesRepository.save(notes.get());

        TaskNotesResponse response = new TaskNotesResponse();
        BeanUtils.copyProperties(taskNotes,response);
        return response;
    }

    @Override
    public Map<String, String> deleteTask(UUID taskUUID) {
        Optional<TaskNotes> taskNotes = taskNotesRepository.findById(taskUUID);

        if(!taskNotes.isPresent())
            throw new BadRequestException("Task já foi deletada");

        Map<String,String> response = new HashMap<>();
        response.put("message",taskNotes.get().getDescription() + " deletado com sucesso!");
        taskNotesRepository.deleteById(taskUUID);

        return response;
    }

    @Override
    public TaskNotesResponse putTaskNotes(TaskNotes taskNotes) {

        if(taskNotes.getDescription() == null || taskNotes.getDescription().equals("") || taskNotes.getStatus() == null)
            throw new BadRequestException("Preencha todos os campos!");

        taskNotesRepository.save(taskNotes);
        TaskNotesResponse response = new TaskNotesResponse();
        BeanUtils.copyProperties(taskNotes,response);
        return response;
    }
}
