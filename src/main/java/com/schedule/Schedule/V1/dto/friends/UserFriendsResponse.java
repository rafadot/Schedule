package com.schedule.Schedule.V1.dto.friends;

import com.schedule.Schedule.V1.dto.users.UsersResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendsResponse {

    private long totalFriends;
    private List<FriendsResponse> friendsResponse;
}
