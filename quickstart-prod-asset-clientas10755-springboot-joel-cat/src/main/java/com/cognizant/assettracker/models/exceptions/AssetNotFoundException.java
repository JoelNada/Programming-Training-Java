package com.cognizant.assettracker.models.exceptions;

public class AssetNotFoundException extends RuntimeException{
    public AssetNotFoundException(String msg){
        super(msg);    }
}
