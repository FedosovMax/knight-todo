package com.knighttodo.todocore.exception;

public class UpdateRoutineException extends RuntimeException {

    public UpdateRoutineException(String message) {
        super(message);
    }

    public UpdateRoutineException(String message, Throwable cause) {
        super(message, cause);
    }
}
