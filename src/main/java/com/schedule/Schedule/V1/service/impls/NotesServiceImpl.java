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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;
    private final ScheduleRepository  scheduleRepository;

    @Override
    public NotesResponse create(NotesRequest notesRequest, UUID scheduleUUID) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleUUID);

        if(!schedule.isPresent())
            throw new BadRequestException("Erro ao criar nota, id da agenda incorreto");

        Notes notes = new Notes();
        BeanUtils.copyProperties(notesRequest , notes);
        notesRepository.save(notes);
        schedule.get().getNotes().add(notes);
        scheduleRepository.save(schedule.get());

        NotesResponse response = new NotesResponse();
        BeanUtils.copyProperties(notes,response);

        return response;
    }

    @Override
    public Map<String, String> delete(UUID uuid) {
        Optional<Notes> notes = notesRepository.findById(uuid);

        if(!notes.isPresent())
            throw new BadRequestException("Erro ao deletar nota, id incorreto");

        String title = notes.get().getTitle();
        notesRepository.deleteById(uuid);
        Map<String, String> response = new HashMap<>();
        response.put("message", title + " excluído com sucesso!");
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
                .time(notesRequest.getTime() != null ? notesRequest.getTime() : optNotes.get().getTime())
                .color(notesRequest.getColor() != null ? notesRequest.getColor() : optNotes.get().getColor())
                .taskNotes(optNotes.get().getTaskNotes())
                .build();

        notesRepository.save(notes);

        NotesResponse response = new NotesResponse();
        BeanUtils.copyProperties(notes,response);

        return response;
    }

    @Override
    public List<NotesResponse> getAll(UUID scheduleUUID) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleUUID);

        if(!schedule.isPresent())
            throw new BadRequestException("Id da agenda não encontrado");

        return schedule.get().getNotes().stream()
                .map(m->NotesResponse.builder()
                        .uuid(m.getUuid())
                        .title(m.getTitle())
                        .color(m.getColor())
                        .taskNotes(m.getTaskNotes())
                        .description(m.getDescription())
                        .date(m.getDate())
                        .time(m.getTime())
                        .build()).collect(Collectors.toList());
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
