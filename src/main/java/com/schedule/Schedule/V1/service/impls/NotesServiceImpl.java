package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.notes.NotesRequest;
import com.schedule.Schedule.V1.dto.notes.NotesResponse;
import com.schedule.Schedule.V1.model.Notes;
import com.schedule.Schedule.V1.model.Schedule;
import com.schedule.Schedule.V1.repository.NotesRepository;
import com.schedule.Schedule.V1.repository.ScheduleRepository;
import com.schedule.Schedule.V1.service.interfaces.NotesService;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;
    private final ScheduleRepository  scheduleRepository;

    @Override
    public NotesResponse create(NotesRequest notesRequest, UUID scheduleUUID) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleUUID);

        if(!schedule.isPresent())
            throw new BadRequestException("Erro ao criar nota, id incorreto");

        Notes notes = new Notes();
        BeanUtils.copyProperties(notesRequest , notes);
        notesRepository.save(notes);
        schedule.get().getNotes().add(notes);
        scheduleRepository.save(schedule.get());

        return NotesResponse.builder()
                .uuid(notes.getUuid())
                .title(notes.getTitle())
                .description(notes.getDescription())
                .date(notes.getDate())
                .build();
    }

    @Override
    public Map<String, String> delete(UUID uuid) {
        Optional<Notes> notes = notesRepository.findById(uuid);

        if(!notes.isPresent())
            throw new BadRequestException("Erro ao deletar nota, id incorreto");

        String title = notes.get().getTitle();
        notesRepository.deleteById(uuid);
        Map<String, String> response = new HashMap<>();
        response.put("messenger", title + " excluído com sucesso!");
        return response;
    }

    @Override
    public NotesResponse update(UUID uuid, NotesRequest notesRequest) {
        Optional<Notes> optNotes = notesRepository.findById(uuid);

        if(!optNotes.isPresent())
            throw new BadRequestException("Id não encontrado");

        Notes notes = Notes.builder()
                .uuid(uuid)
                .title(notesRequest.getTitle() != null ? notesRequest.getTitle() : optNotes.get().getTitle())
                .description(notesRequest.getDescription() != null ? notesRequest.getDescription() : optNotes.get().getDescription())
                .date(notesRequest.getDate() != null ? notesRequest.getDate() : optNotes.get().getDate())
                .build();

        notesRepository.save(notes);

        NotesResponse response = new NotesResponse();
        BeanUtils.copyProperties(notes,response);

        return response;
    }

    @Override
    public List<Notes> getAll(UUID scheduleUUID) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleUUID);

        if(!schedule.isPresent())
            throw new BadRequestException("Id da agenda não encontrado");

        return schedule.get().getNotes();
    }

    @Override
    public NotesResponse getById(UUID uuid) {
        Optional<Notes> optNotes = notesRepository.findById(uuid);

        if(!optNotes.isPresent())
            throw new BadRequestException("Id da nota não encontrado");

        Notes notes = optNotes.get();
        NotesResponse response = new NotesResponse();
        BeanUtils.copyProperties(notes,response);

        return response;
    }
}
