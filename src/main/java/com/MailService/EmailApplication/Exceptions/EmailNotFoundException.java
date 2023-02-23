package com.MailService.EmailApplication.Exceptions;

public class EmailNotFoundException extends  RuntimeException{

    public EmailNotFoundException(String message){
        super(message);
    }
    public EmailNotFoundException(Long emailId) {
        this("Email with " + emailId +" was not found");
    }
}
