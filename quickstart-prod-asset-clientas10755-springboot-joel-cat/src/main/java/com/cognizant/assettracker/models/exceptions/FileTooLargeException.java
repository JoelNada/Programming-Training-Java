package com.cognizant.assettracker.models.exceptions;

public class FileTooLargeException extends RuntimeException{
    public FileTooLargeException(String name){
        super(name+" exceeds size limit. Please upload files less than 5MB");
    }
}
