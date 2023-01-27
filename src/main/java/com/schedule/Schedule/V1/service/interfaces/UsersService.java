package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.users.UsersRequest;
import com.schedule.Schedule.V1.dto.users.UsersResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UsersService {
    UsersResponse createUser(UsersRequest usersRequest);

    List<UsersResponse> getAll();

    UsersResponse getUserById(UUID uuid);

    Map<String, String> delete(UUID uuid);

    UsersResponse addFriend(UUID user, UUID friend);

    List<UsersResponse> getFriendsUser(UUID userUUID);
}
