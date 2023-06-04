package com.example.apirest.exceptions;

public class UserCreationException extends RuntimeException{

    public UserCreationException(String message){
        super(message);
    }
}
