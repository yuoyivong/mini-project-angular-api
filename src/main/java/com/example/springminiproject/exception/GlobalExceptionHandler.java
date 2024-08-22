package com.example.springminiproject.exception;

import com.example.springminiproject.response.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

//    not found exception
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody CustomErrorResponse handleNotFoundException(NotFoundException notFoundException) {
        return new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                notFoundException.getMessage()

        );
    }

//    not allow blank field exception
    @ExceptionHandler(value = BlankFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody CustomErrorResponse handleBlankFieldException(BlankFieldException exception) {
        return new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()

        );
    }

//    duplicate inserted value exception
    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody CustomErrorResponse handleAlreadyExistsException(AlreadyExistsException exception) {
        return new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );
    }

    //    access denied exception
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody CustomErrorResponse handleAccessDeniedException(AccessDeniedException exception) {
        return new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage()

        );
    }

    //    email or phone number does not match the pattern
    @ExceptionHandler(value = RegexPatternException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody CustomErrorResponse handleRegexPatternException(RegexPatternException exception) {
        return new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );
    }

    //    role mismatches READER or AUTHOR
    @ExceptionHandler(value = MismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody CustomErrorResponse handleInvalidFieldException(MismatchException exception) {
        return new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );
    }


}
