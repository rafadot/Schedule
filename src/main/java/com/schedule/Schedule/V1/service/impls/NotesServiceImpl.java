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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
}
