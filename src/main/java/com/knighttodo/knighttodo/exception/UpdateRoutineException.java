package com.knighttodo.knighttodo.exception;

public class UpdateRoutineException extends RuntimeException {

    public UpdateRoutineException(String message) {
        super(message);
    }

    public UpdateRoutineException(String message, Throwable cause) {
        super(message, cause);
    }
}
