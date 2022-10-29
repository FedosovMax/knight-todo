package com.knighttodo.todocore.exception;

public class UnchangeableFieldUpdateException extends RuntimeException {

    public UnchangeableFieldUpdateException(String message) {
        super(message);
    }
}
