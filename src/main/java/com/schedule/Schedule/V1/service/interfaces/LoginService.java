package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.model.Login;

public interface LoginService {

    UsersResponse login(Login login);
}
