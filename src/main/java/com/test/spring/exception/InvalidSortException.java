package com.test.spring.exception;

public class InvalidSortException extends IllegalArgumentException{
    public InvalidSortException(String message){
        super(message);
    }
}
