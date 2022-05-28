package com.equilator.exceptions;

public class InvalidInputCards extends RuntimeException {

    public InvalidInputCards() {
        super();
    }

    public InvalidInputCards(String message) {
        super(message);
    }
}
