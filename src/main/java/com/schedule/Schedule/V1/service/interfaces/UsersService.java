package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.users.UsersRequest;
import com.schedule.Schedule.V1.dto.users.UsersResponse;

import java.util.List;

public interface UsersService {
    UsersResponse createUser(UsersRequest usersRequest);

    List<UsersResponse> getAll();
}
