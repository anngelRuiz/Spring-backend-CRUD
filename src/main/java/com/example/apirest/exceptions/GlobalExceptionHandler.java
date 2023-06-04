package com.example.apirest.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException(UserNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setErrorMessage("User not Found");
        errorResponse.setErrorDetails(ex.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setUrl(request.getRequestURL().toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    public ResponseEntity<ErrorResponse> handlerNumberFormatException(NumberFormatException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorMessage("Invalid ID format");
        errorResponse.setErrorDetails(ex.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setUrl(request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ErrorResponse> handlerUserCreationException(UserCreationException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorMessage("Failed to create user");
        errorResponse.setErrorDetails(ex.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setUrl(request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

//    @ExceptionHandler(value = { ResourceNotFoundException.class })
//    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }


//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleUserNotFoundException(UserNotFoundException ex){
//        return ex.getMessage();
//    }

    /*
        handleUserNotFoundException  method approaches -

            Approach 1 returning ResponseEntity -
            If you want to provide more flexibility in customizing the response, such as adding custom headers or using a ResponseEntity with additional features, you can choose the first implementation:

            Approach 2 return just String -
            A more concise approach and don't need any additional customizations
    */
}