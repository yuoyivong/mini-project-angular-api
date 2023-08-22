package com.example.library_management.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail handleUserNotFoundException(UserNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setType(URI.create("localhost:8080/error/"));
        problemDetail.setTitle("This user doesn't exist in the world!");
        problemDetail.setProperty("time : ", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler
    ProblemDetail handleBlankFieldException(BlankFieldExceptionHandler e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    ProblemDetail handleNotValidException(MethodArgumentNotValidException e) {
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
//        return problemDetail;
//    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<String>> handleValidationError(MethodArgumentNotValidException exception) {
        Map<String, List<String>> errorMap = new HashMap<>();
        List<String> errors = new ArrayList<>();
        if(exception.hasErrors()) {
            for(ObjectError error : exception.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            errorMap.put("errors : ", errors);
        }

        return errorMap;

    }
}