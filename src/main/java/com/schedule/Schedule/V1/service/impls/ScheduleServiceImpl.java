package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.schedule.ScheduleResponse;
import com.schedule.Schedule.V1.model.Schedule;
import com.schedule.Schedule.V1.model.Users;
import com.schedule.Schedule.V1.repository.ScheduleRepository;
import com.schedule.Schedule.V1.repository.UsersRepository;
import com.schedule.Schedule.V1.service.interfaces.ScheduleService;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final UsersRepository usersRepository;

    @Override
    public ScheduleResponse getByScheduleId(UUID scheduleId) {
        Optional<Schedule> optSchedule = scheduleRepository.findById(scheduleId);

        if(!optSchedule.isPresent())
            throw new BadRequestException("Id não encontrado");

        Schedule schedule = optSchedule.get();
        ScheduleResponse response = new ScheduleResponse();
        BeanUtils.copyProperties(schedule,response);

        return response;
    }

    @Override
    public ScheduleResponse getByUserId(UUID userId) {
        Optional<Users> optUsers = usersRepository.findById(userId);

        if(!optUsers.isPresent())
            throw new BadRequestException("Id não encontrado");

        Schedule schedule = optUsers.get().getSchedule();
        ScheduleResponse response = new ScheduleResponse();
        BeanUtils.copyProperties(schedule,response);

        return response;
    }

//    @Override
//    public ScheduleResponse create(UUID usersUUID) {
//        Optional<Users> users = usersRepository.findById(usersUUID);
//
//        if(!users.isPresent())
//            throw new BadRequestException("Id desconhecido");
//
//        Schedule schedule = new Schedule();
//
//        scheduleRepository.save(schedule);
//        users.get().setSchedule(schedule);
//        usersRepository.save(users.get());
//
//        return ScheduleResponse.builder()
//                .uuid(schedule.getUuid())
//                .contacts(schedule.getContacts())
//                .build();
//    }


}
