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
        Optional<Users> users = usersRepository.findByEmail(login.getEmailOrNickName());

        if(!users.isPresent()){
            users = usersRepository.findByNickName(login.getEmailOrNickName());

            if(!users.isPresent())
                throw new BadRequestException("Email ou username inválido");
        }

        if(!encoder.matches(login.getPassword(), users.get().getPassword()))
            throw new BadRequestException("Senha inválida");

        Map<String, UUID> response = new HashMap<>();

        response.put("userId",users.get().getUuid());
        response.put("scheduleId",users.get().getSchedule().getUuid());

        return response;
    }
}
