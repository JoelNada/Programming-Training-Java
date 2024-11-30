package com.cognizant.assettracker.models.exceptions;

public class UnsupportedFileException extends RuntimeException {
    public UnsupportedFileException(String filename) {
        super(filename + " is not supported, file must be excel or csv");
    }
}
