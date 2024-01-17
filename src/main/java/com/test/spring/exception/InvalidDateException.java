package com.test.spring.exception;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String message){
        super(message);
    }
}
