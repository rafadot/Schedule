package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.model.Login;
import com.schedule.Schedule.V1.model.Users;
import com.schedule.Schedule.V1.repository.UsersRepository;
import com.schedule.Schedule.V1.service.interfaces.LoginService;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder encoder;

    public Map<String, UUID> login(Login login) {
        Optional<Users> optUsers;
        Users users = new Users();

        if(login.getType().toString().equals("EMAIL")){
            optUsers = usersRepository.findByEmail(login.getEmailOrNickName());
            if(!optUsers.isPresent())
                throw new BadRequestException("Email não cadastrado");

            users = optUsers.get();

        }else if(login.getType().toString().equals("NICKNAME")){
            optUsers = usersRepository.findByNickName(login.getEmailOrNickName());
            if(!optUsers.isPresent())
                throw new BadRequestException("Usuário não cadastrado");

            users = optUsers.get();

        }

        if(!encoder.matches(login.getPassword(), users.getPassword()))
            throw new BadRequestException("Senha inválida");

        Map<String, UUID> response = new HashMap<>();

        response.put("userId",users.getUuid());
        response.put("scheduleId",users.getSchedule().getUuid());

        return response;
    }
}
