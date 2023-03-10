package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.events.EventsRequest;
import com.schedule.Schedule.V1.dto.events.EventsResponse;
import com.schedule.Schedule.V1.model.Events;
import com.schedule.Schedule.V1.model.Schedule;
import com.schedule.Schedule.V1.model.Users;
import com.schedule.Schedule.V1.repository.EventsRepository;
import com.schedule.Schedule.V1.repository.ScheduleRepository;
import com.schedule.Schedule.V1.repository.UsersRepository;
import com.schedule.Schedule.V1.service.interfaces.EventsService;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final EventsRepository eventsRepository;
    private final ScheduleRepository scheduleRepository;
    private final UsersRepository usersRepository;


    @Override
    public EventsResponse create(UUID scheduleUUID, EventsRequest eventsRequest) {
        Optional<Schedule> optSchedule = scheduleRepository.findById(scheduleUUID);

        if(!optSchedule.isPresent())
            throw new BadRequestException("Id da agenda não encontrado");

        List<Events> eventsList = optSchedule.get().getEvents();

        for(Events event : eventsList){
            if(event.getTitle().equals(eventsRequest.getTitle()))
                throw new BadRequestException("Nome de evento já existe");
        }

        Events events = new Events();
        Schedule schedule = optSchedule.get();

        BeanUtils.copyProperties(eventsRequest,events);

        events.setCreator(schedule.getCreatorName());
        eventsRepository.save(events);
        schedule.getEvents().add(events);
        scheduleRepository.save(schedule);

        EventsResponse response = new EventsResponse();
        BeanUtils.copyProperties(events,response);

        return response;
    }

    @Override
    public EventsResponse patchEvent(UUID scheduleUUID, UUID eventUUID, EventsRequest eventsRequest) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleUUID);

        if(!schedule.isPresent())
            throw new BadRequestException("Id da agenda inválido");

        Optional<Events> optEvents = eventsRepository.findById(eventUUID);

        if(!optEvents.isPresent()) {
            throw new BadRequestException("Id do evento inválido");
        }

        if(!schedule.get().getCreatorName().equals(optEvents.get().getCreator()))
            throw new BadRequestException("Apenas o criador do evento (" + optEvents.get().getCreator() + ") pode modificalo-lo");

        for(Events events : schedule.get().getEvents()){
            if(events.getTitle().equals(eventsRequest.getTitle()) && (!events.getUuid().equals(eventUUID)))
                throw new BadRequestException("Nome do evento já existe");
        }

        Events events = Events.builder()
                .uuid(eventUUID)
                .title(eventsRequest.getTitle() != null ? eventsRequest.getTitle() : optEvents.get().getTitle())
                .description(eventsRequest.getDescription() != null ? eventsRequest.getDescription() : optEvents.get().getDescription())
                .place(eventsRequest.getPlace() != null ? eventsRequest.getPlace() : optEvents.get().getPlace())
                .startDate(eventsRequest.getStartDate() != null ? eventsRequest.getStartDate() : optEvents.get().getStartDate())
                .endDate(eventsRequest.getEndDate() != null ? eventsRequest.getEndDate() : optEvents.get().getEndDate())
                .creator(optEvents.get().getCreator())
                .build();
        eventsRepository.save(events);

        EventsResponse eventsResponse = new EventsResponse();
        BeanUtils.copyProperties(events,eventsResponse);

        return eventsResponse;
    }

    @Override
    public Map<String, String> group(UUID eventUUID, String friendNickName) {
        Optional<Events> optEvent = eventsRepository.findById(eventUUID);

        if(!optEvent.isPresent())
            throw new BadRequestException("Id do evento inválido");

        Optional<Users> friend = usersRepository.findByNickName(friendNickName);

        if(!friend.isPresent())
            throw new BadRequestException("Apelido do amigo inválido");

        List<Events> eventsList = friend.get().getSchedule().getEvents();

        for(Events event : eventsList){
            if(event.getCreator().equals(optEvent.get().getCreator()) && event.getTitle().equals(optEvent.get().getTitle()))
                throw new BadRequestException(friendNickName + " usuário já possui esse evento");
        }

        Users users = friend.get();

        users.getSchedule().getEvents().add(optEvent.get());
        usersRepository.save(users);

        Map<String, String> response = new HashMap<>();

        response.put("message",optEvent.get().getTitle() + " compartilhado com " + friendNickName);

        return response;
    }

    @Override
    public List<EventsResponse> getAll(UUID scheduleUUID) {
        Optional<Schedule> optSchedule = scheduleRepository.findById(scheduleUUID);

        if(!optSchedule.isPresent())
            throw new BadRequestException("Id da agenda inválido");

        return optSchedule.get().getEvents().stream()
                .map(m->{
                    EventsResponse response = new EventsResponse();
                    BeanUtils.copyProperties(m,response);
                    return response;
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteEvents(UUID eventsUUID) {
        eventsRepository.deleteEventAndScheduleRelations(eventsUUID);
    }

}
