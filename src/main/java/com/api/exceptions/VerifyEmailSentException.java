package com.api.exceptions;

public class VerifyEmailSentException extends RuntimeException{
    public VerifyEmailSentException(String message) {
        super(message);
    }
}
