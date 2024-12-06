package com.example.burrrrng.exception;

public class StoreLimitException extends RuntimeException {
    
    public StoreLimitException(String message) {
        super(message);
    }
}