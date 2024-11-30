package com.cognizant.assettracker.models.exceptions;

import org.springframework.http.HttpStatus;

public class EPLException extends RuntimeException{
    public EPLException(String msg){
        super(msg);
    }
}
