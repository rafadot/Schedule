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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder encoder;

    @Override
    public UsersResponse login(Login login) {
        Optional<Users> optUsers = usersRepository.findByEmail(login.getEmail());

        if(!optUsers.isPresent())
            throw new BadRequestException("Email não cadastrado");
        
        Users users = optUsers.get();

        if(!encoder.matches(login.getPassword(), users.getPassword()))
            throw new BadRequestException("Senha inválida");

        UsersResponse response = new UsersResponse();
        BeanUtils.copyProperties(users,response);

        return response;
    }
}
