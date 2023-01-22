package com.schedule.Schedule.V1.controller;

import com.schedule.Schedule.V1.dto.users.UsersResponse;
import com.schedule.Schedule.V1.model.Login;
import com.schedule.Schedule.V1.service.interfaces.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    ResponseEntity<Map<String, UUID>> login(@Valid @RequestBody Login login){
        return new ResponseEntity<>(loginService.login(login), HttpStatus.OK);
    }
}
