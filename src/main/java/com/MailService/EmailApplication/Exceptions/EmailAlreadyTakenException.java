package com.MailService.EmailApplication.Exceptions;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
