package com.cognizant.assettracker.models.exceptions;


public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String msg){
        super(msg);
    }
}
