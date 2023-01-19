package com.schedule.Schedule.V1.service.impls;

import com.schedule.Schedule.V1.mapper.UsersMapper;
import com.schedule.Schedule.V1.model.Users;
import com.schedule.Schedule.V1.dto.users.UsersRequest;
import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.repository.UsersRepository;
import com.schedule.Schedule.V1.service.interfaces.UsersService;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder encoder;

    private final UsersMapper usersMapper;

    @Override
    public UsersResponse createUser(UsersRequest usersRequest) {
        Optional<Users> usersOptional = usersRepository.findByEmail(usersRequest.getEmail());

        if(usersOptional.isPresent()){
            throw new BadRequestException("O email já está cadastrado");
        }

        Users users = new Users();
        BeanUtils.copyProperties(usersRequest, users);

        users.setPassword(encoder.encode(users.getPassword()));
        usersRepository.save(users);

        return UsersResponse.builder()
                .uuid(users.getUuid())
                .email(users.getEmail())
                .fullName(users.getFullName())
                .birthDate(users.getBirthDate())
                .genre(users.getGenre())
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
}
