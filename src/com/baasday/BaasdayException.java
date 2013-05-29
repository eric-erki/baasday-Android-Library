package com.baasday;

public class BaasdayException extends Exception {
    public BaasdayException(final Throwable cause) {
        super(cause);
    }

    public BaasdayException(final String message) {
        super(message);
    }
}
