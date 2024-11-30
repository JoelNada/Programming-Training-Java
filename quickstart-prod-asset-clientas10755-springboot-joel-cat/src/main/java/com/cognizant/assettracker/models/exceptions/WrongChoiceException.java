package com.cognizant.assettracker.models.exceptions;

public class WrongChoiceException extends RuntimeException{
    public WrongChoiceException(String msg){
        super(msg);
    }
}
