package com.cognizant.assettracker.models.exceptions;

public class UnauthorizedRoleException extends RuntimeException{
    public UnauthorizedRoleException(String msg){
        super(msg);
    }
}
