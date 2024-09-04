package com.api.exceptions;

public class VerifyEmailSendedException extends RuntimeException{
    public VerifyEmailSendedException(String message) {
        super(message);
    }
}
