package com.schedule.Schedule.exceptions.gerenciment;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String msg){
        super(msg);
    }
}
