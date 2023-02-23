package com.MailService.EmailApplication.Exceptions;

public class WrongEmailAddressFormatException extends RuntimeException{

    public WrongEmailAddressFormatException(String message){
        super(message);
    }
}
