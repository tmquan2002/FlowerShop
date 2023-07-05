package com.example.flowershop.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("Username already exists");
    }
}
