package com.cognizant.assettracker.models.exceptions;

public class UserEmailNotFoundException extends RuntimeException{
    public UserEmailNotFoundException(String msg){
        super(msg);
    }
}
