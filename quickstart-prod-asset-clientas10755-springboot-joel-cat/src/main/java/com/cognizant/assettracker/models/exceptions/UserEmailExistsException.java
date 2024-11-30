package com.cognizant.assettracker.models.exceptions;

public class UserEmailExistsException extends RuntimeException{
    public UserEmailExistsException(String msg){
        super(msg);
    }
}
