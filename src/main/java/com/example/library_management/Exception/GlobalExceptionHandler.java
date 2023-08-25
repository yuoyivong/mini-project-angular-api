package com.example.library_management.Exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;

//@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ProblemDetail handleUserNotFoundException(NotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
//        problemDetail.setType(URI.create("localhost:8080/error/"));
//        problemDetail.setTitle("This user doesn't exist in the world!");
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

//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String, List<String>> handleValidationError(MethodArgumentNotValidException exception) {
//        Map<String, List<String>> errorMap = new HashMap<>();
//        List<String> errors = new ArrayList<>();
//        if(exception.hasErrors()) {
//            for(ObjectError error : exception.getAllErrors()) {
//                errors.add(error.getDefaultMessage());
//            }
//            errorMap.put("errors : ", errors);
//        }
//
//        return errorMap;
//
//    }

    @ExceptionHandler(IfAlreadyExistValidationException.class)
    ProblemDetail handleIfAlreadyExistException(IfAlreadyExistValidationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
//        problemDetail.setType(URI.create("localhost:8080/error/"));
//        problemDetail.setTitle("This user doesn't exist in the world!");
        problemDetail.setProperty("time : ", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(InvalidImageException.class)
    ProblemDetail handleInvalidImageException(InvalidImageException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}