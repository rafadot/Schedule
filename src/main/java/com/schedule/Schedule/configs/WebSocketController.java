package com.schedule.Schedule.configs;

import com.schedule.Schedule.V1.model.Users;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebSocketController {

    @MessageMapping("/notification")
    @SendTo("/topic/notifications")
    public void sendNotification(String message) {
        System.out.println(message);
    }
}
