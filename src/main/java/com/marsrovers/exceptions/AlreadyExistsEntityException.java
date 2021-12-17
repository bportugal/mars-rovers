package com.marsrovers.exceptions;

public class AlreadyExistsEntityException extends RuntimeException {

    public AlreadyExistsEntityException(String message) {
        super(message);
    }
}
