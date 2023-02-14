package com.schedule.Schedule.exceptions.handler;

import com.schedule.Schedule.exceptions.BodyError;
import com.schedule.Schedule.exceptions.gerenciment.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BodyError> badRequest(BadRequestException e, HttpServletRequest request){

        BodyError bodyError = BodyError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Requisição mal feita")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(bodyError,HttpStatus.BAD_REQUEST);
    }
}
