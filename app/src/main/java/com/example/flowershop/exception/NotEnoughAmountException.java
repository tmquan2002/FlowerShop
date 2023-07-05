package com.example.flowershop.exception;

public class NotEnoughAmountException extends RuntimeException{
    public NotEnoughAmountException() {
        super("Not enough amount");
    }
}
