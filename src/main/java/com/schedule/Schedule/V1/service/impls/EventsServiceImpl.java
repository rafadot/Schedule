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
    public EventsResponse patchEvent(UUID eventUUID, EventsRequest eventsRequest) {
        Optional<Events> optEvents = eventsRepository.findById(eventUUID);

        if(!optEvents.isPresent())
            throw new BadRequestException("Id do evento inválido");

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
        Optional<Users> optUsers = usersRepository.findByNickName(friendNickName);

        if(!optEvent.isPresent() && !optUsers.isPresent())
            throw new BadRequestException("Id do evento e do usuário inválido");

        if(!optEvent.isPresent())
            throw new BadRequestException("Id do evento inválido");

        if(!optUsers.isPresent())
            throw new BadRequestException("Id do usuário inválido");

        List<Events> eventsList = optUsers.get().getSchedule().getEvents();

        for(Events event : eventsList){
            if(event.getCreator().equals(optEvent.get().getCreator()) && event.getTitle().equals(optEvent.get().getTitle()))
                throw new BadRequestException(friendNickName + " usuário já possui esse evento");
        }

        Events events = new Events();
        Events events1 = optEvent.get();
        Users users = optUsers.get();

        BeanUtils.copyProperties(events1,events,"uuid");
        eventsRepository.save(events);

        users.getSchedule().getEvents().add(events);
        usersRepository.save(users);

        Map<String, String> response = new HashMap<>();

        response.put("message",events.getTitle() + " compartilhado com " + friendNickName);

        return response;
    }

    @Override
    public List<Events> getAll(UUID scheduleUUID) {
        Optional<Schedule> optSchedule = scheduleRepository.findById(scheduleUUID);

        if(!optSchedule.isPresent())
            throw new BadRequestException("Id da agenda inválido");

        return optSchedule.get().getEvents();
    }
}
