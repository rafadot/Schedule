package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.users.UsersRequest;
import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.service.interfaces.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @DeleteMapping
    public ResponseEntity<Map<String,String>> delete(@RequestParam UUID uuid){
        return new ResponseEntity<>(usersService.delete(uuid) ,HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UsersResponse> getUserById(@RequestParam UUID uuid){
        return new ResponseEntity<>(usersService.getUserById(uuid),HttpStatus.OK);
    }

}
