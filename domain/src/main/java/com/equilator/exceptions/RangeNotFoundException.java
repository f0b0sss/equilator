package com.equilator.exceptions;

public class RangeNotFoundException  extends RuntimeException{
    public RangeNotFoundException() {
        super();
    }

    public RangeNotFoundException(String message) {
        super(message);
    }
}
