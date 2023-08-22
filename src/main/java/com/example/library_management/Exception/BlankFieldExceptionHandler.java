package com.example.library_management.Exception;

public class BlankFieldExceptionHandler extends RuntimeException {

    public BlankFieldExceptionHandler(String message) {
        super(message);
    }
}