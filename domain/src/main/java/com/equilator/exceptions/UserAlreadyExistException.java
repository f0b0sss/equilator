package com.equilator.exceptions;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException() {
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
