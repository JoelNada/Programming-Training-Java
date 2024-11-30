package com.cognizant.assettracker.models.exceptions;

import org.springframework.http.HttpStatus;

public class EmployeeDocumentException extends RuntimeException{
    public EmployeeDocumentException(String msg){
        super (msg);
    }
}
