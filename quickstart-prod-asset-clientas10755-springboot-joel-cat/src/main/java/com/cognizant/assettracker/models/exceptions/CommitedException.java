package com.cognizant.assettracker.models.exceptions;

public class CommitedException extends RuntimeException {
    public CommitedException(String msg){
        super(msg);
    }
}
