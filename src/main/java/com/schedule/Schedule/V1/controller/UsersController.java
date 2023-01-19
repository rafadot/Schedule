package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.users.UsersRequest;
import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.service.interfaces.UsersService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    public ResponseEntity<UsersResponse> createUser(@RequestBody UsersRequest usersRequest){
        return new ResponseEntity<>(usersService.createUser(usersRequest) , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsersResponse>> getAll(){
        return new ResponseEntity<>(usersService.getAll() , HttpStatus.OK);
    }

}
