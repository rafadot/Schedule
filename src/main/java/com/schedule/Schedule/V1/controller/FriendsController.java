package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.users.UserFriendsResponse;
import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.service.interfaces.FriendsService;
import com.schedule.Schedule.V1.service.interfaces.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    @GetMapping("/addFriend")
    public ResponseEntity<UsersResponse> addFriend(@RequestParam UUID userUUID, @RequestParam UUID friendUUID){
        return new ResponseEntity<>(friendsService.addFriend(userUUID,friendUUID), HttpStatus.OK);
    }

    @GetMapping("/getFriendsUser")
    public ResponseEntity<UserFriendsResponse> getUserFriends(@RequestParam UUID userUUID){
        return new ResponseEntity<>(friendsService.getFriendsUser(userUUID),HttpStatus.OK);
    }

    @DeleteMapping("/deleteFriend")
    public ResponseEntity<Map<String,String>> deleteFriend(@RequestParam UUID userUUID, @RequestParam UUID friendUUID){
        return new ResponseEntity<>(friendsService.deleteFriend(userUUID,friendUUID),HttpStatus.OK);
    }
}