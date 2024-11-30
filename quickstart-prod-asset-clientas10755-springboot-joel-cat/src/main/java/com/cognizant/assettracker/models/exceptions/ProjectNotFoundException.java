package com.cognizant.assettracker.models.exceptions;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String msg){
        super(msg);
    }
}
