package com.example.springminiproject.exception;

import com.example.springminiproject.response.CustomErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Set;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody CustomErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        String errorMessage = "Invalid input provided";

        if (mostSpecificCause instanceof InvalidFormatException invalidFormatException) {
            String targetType = invalidFormatException.getTargetType().getSimpleName();
            String invalidValue = invalidFormatException.getValue().toString();
            String normalizedValue = invalidValue.toUpperCase();

            // Define available roles (or other enum values) in uppercase
            Set<String> availableValues = Set.of("AUTHOR", "READER");

            if (!availableValues.contains(normalizedValue)) {
                errorMessage = String.format("Invalid value '%s' for %s (available roles are %s)",
                        invalidValue, targetType, String.join(", ", availableValues));
            }
        }

        return new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errorMessage
        );
    }
}
