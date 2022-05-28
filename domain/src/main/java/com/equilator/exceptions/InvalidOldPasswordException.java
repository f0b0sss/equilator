package com.equilator.exceptions;

public class InvalidOldPasswordException extends RuntimeException{

    public InvalidOldPasswordException() {
        super();
    }

    public InvalidOldPasswordException(String message) {
        super(message);
    }
}
