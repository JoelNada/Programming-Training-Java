package com.cognizant.assettracker.models.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JWTException extends AuthenticationException {
    public JWTException(String message) {

        super("JWT Exception: "+message);
    }
}
