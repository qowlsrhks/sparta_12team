package com.api.domain.common;

import com.api.exceptions.VerifyEmailSentException;
import jakarta.mail.MessagingException;
import com.api.exceptions.DeactivatedUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400
    @ExceptionHandler({IllegalArgumentException.class,
            NullPointerException.class, DeactivatedUserException.class})
    public ResponseEntity<String> BadRequestException(final RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 500
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<String> MessagingException(final RuntimeException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    //403
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<String> ForbiddenException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    //202
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<String> VerifyEmailSentException(VerifyEmailSentException e) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
    }

}