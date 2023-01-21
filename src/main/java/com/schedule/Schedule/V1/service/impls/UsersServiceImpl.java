package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.mapper.UsersMapper;
import com.schedule.Schedule.V1.model.Schedule;
import com.schedule.Schedule.V1.model.Users;
import com.schedule.Schedule.V1.dto.users.UsersRequest;
import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.repository.ScheduleRepository;
import com.schedule.Schedule.V1.repository.UsersRepository;
import com.schedule.Schedule.V1.service.interfaces.UsersService;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;
    private final UsersMapper usersMapper;
    private final ScheduleRepository scheduleRepository;

    @Override
    public UsersResponse createUser(UsersRequest usersRequest) {
        Optional<Users> email = usersRepository.findByEmail(usersRequest.getEmail());
        Optional<Users> nickName = usersRepository.findByNickName(usersRequest.getNickName());

        if(email.isPresent() && nickName.isPresent()){
            throw new BadRequestException("Email e nome de usuário já cadastrados");
        }else if(email.isPresent()){
            throw new BadRequestException("Email já cadastrado");
        }else if(nickName.isPresent()){
            throw new BadRequestException("Nome de usuário já cadastrado");
        }

        Users users = new Users();
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(usersRequest, users);

        scheduleRepository.save(schedule);
        users.setPassword(encoder.encode(users.getPassword()));
        users.setSchedule(schedule);
        usersRepository.save(users);

        return UsersResponse.builder()
                .uuid(users.getUuid())
                .email(users.getEmail())
                .fullName(users.getFullName())
                .nickName(users.getNickName())
                .birthDate(users.getBirthDate())
                .genre(users.getGenre())
                .schedule(users.getSchedule())
                .build();
    }

    @Override
    public List<UsersResponse> getAll() {
        return usersRepository.findAll()
                .stream()
                .map(m ->{
                    UsersResponse usersResponse = new UsersResponse();
                    usersMapper.usersResponseMapper(m, usersResponse);
                    return usersResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> delete(UUID uuid) {
        Optional<Users> users = usersRepository.findById(uuid);

        if(!users.isPresent())
            throw new BadRequestException("Id não inválido");

        String name = users.get().getNickName();
        usersRepository.deleteById(uuid);
        Map<String, String> response = new HashMap<>();
        response.put("messenger", name + " exluído com sucesso!");

        return response;
    }
}
