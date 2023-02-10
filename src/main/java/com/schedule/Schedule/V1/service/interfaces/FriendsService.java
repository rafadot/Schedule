package com.schedule.Schedule.V1.service.interfaces;

import com.schedule.Schedule.V1.dto.users.UserFriendsResponse;
import com.schedule.Schedule.V1.dto.users.UsersResponse;

import java.util.Map;
import java.util.UUID;

public interface FriendsService {
    UsersResponse addFriend(UUID user, String friendNickName);

    UserFriendsResponse getFriendsUser(UUID userUUID);

    Map<String,String> deleteFriend(UUID userUUID, String friendNickName);
}
