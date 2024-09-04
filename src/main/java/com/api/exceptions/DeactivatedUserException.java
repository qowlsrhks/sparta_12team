package com.api.exceptions;

public class DeactivatedUserException extends RuntimeException{
    public DeactivatedUserException(String message) {
        super(message);
    }
}
