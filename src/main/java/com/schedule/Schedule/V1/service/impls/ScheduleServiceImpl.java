package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.contacts.ContactsResponse;
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
import java.util.stream.Collectors;

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

        response.setContacts(schedule.getContacts().stream()
                .map(m->ContactsResponse.builder()
                        .uuid(m.getUuid())
                        .name(m.getName())
                        .phoneNumber(m.getPhoneNumber())
                        .nickname(m.getNickname())
                        .build()).collect(Collectors.toList()));

        return response;
    }

    /*Esse metodo foi "usado" para retornar a agenda do usuário através do seu proprio id, porém
    por conta do seu desuso, estará fora do ar.*/

//    @Override
//    public ScheduleResponse getByUserId(UUID userId) {
//        Optional<Users> optUsers = usersRepository.findById(userId);
//
//        if(!optUsers.isPresent())
//            throw new BadRequestException("Id não encontrado");
//
//        Schedule schedule = optUsers.get().getSchedule();
//        ScheduleResponse response = new ScheduleResponse();
//        BeanUtils.copyProperties(schedule,response);
//
//        return response;
//    }

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
