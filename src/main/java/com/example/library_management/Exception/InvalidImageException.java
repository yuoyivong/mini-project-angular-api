package com.example.library_management.Exception;

public class InvalidImageException extends RuntimeException{
    public InvalidImageException(String message){
        super(message);
    }
}
