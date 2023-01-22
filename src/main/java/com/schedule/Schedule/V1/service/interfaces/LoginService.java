package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.model.Login;

import java.util.Map;
import java.util.UUID;

public interface LoginService {

    Map<String, UUID> login(Login login);
}
