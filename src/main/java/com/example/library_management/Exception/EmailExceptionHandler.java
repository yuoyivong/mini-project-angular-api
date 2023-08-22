package com.example.library_management.Exception;

public class EmailExceptionHandler extends RuntimeException{
    public EmailExceptionHandler(String message) {
        super(message);
    }
}
