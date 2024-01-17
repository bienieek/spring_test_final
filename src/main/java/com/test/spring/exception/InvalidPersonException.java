package com.test.spring.exception;

public class InvalidPersonException extends RuntimeException{
    public InvalidPersonException(String message){
        super(message);
    }
}
