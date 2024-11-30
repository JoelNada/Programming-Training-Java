package com.cognizant.assettracker.models.exceptions;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(String msg){
        super(msg);
    }
}
