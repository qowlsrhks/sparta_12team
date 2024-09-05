package com.api.exceptions;

public class ImageUploadFailedException extends RuntimeException {
    public ImageUploadFailedException(String message) {
        super(message);
    }
}
