package com.example.apirest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLOutput;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleResourceNotFoundException(ResourceNotFoundException ex) {
        // log the error or do any other necessary actions
        System.out.println(ex.getMessage());
    }

//    @ExceptionHandler(value = { ResourceNotFoundException.class })
//    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }
}