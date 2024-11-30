package com.cognizant.assettracker.models.exceptions;

public class EPLNotPresentException extends RuntimeException{
    public EPLNotPresentException(String filename){
        super("Requested File is not present:"+filename);
    }
}
