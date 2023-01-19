package com.schedule.Schedule.V1.mapper;

import com.schedule.Schedule.V1.model.Users;
import com.schedule.Schedule.V1.dto.users.UsersResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsersMapper {

    ModelMapper modelMapper;

    public void usersResponseMapper(Users users, UsersResponse usersResponse){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(users,usersResponse);
    }
}
