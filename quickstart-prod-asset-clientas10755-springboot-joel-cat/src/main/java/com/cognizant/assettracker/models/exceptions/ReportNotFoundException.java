package com.cognizant.assettracker.models.exceptions;

public class ReportNotFoundException extends RuntimeException{
    public ReportNotFoundException(String msg){
        super(msg);
    }
}
