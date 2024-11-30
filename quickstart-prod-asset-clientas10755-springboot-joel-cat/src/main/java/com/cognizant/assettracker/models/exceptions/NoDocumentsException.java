package com.cognizant.assettracker.models.exceptions;

public class NoDocumentsException extends RuntimeException{
    public NoDocumentsException(String message){
        super(message);
    }
}
