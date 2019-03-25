package com.calendargenerator.exception;

public final class GenericException extends RuntimeException {

    public GenericException() {
        super();
    }

    public GenericException(final String message) {
        super(message);
    }
}