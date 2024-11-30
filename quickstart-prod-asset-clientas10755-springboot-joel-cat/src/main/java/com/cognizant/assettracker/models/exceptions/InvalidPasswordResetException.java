package com.cognizant.assettracker.models.exceptions;

public class InvalidPasswordResetException extends  RuntimeException{
    public InvalidPasswordResetException(String msg){
        super(msg);
    }
}
