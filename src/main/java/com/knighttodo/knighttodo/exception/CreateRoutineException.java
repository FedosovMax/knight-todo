package com.knighttodo.knighttodo.exception;

public class CreateRoutineException extends RuntimeException {

    public CreateRoutineException(String message) {
        super(message);
    }

    public CreateRoutineException(String message, Throwable cause) {
        super(message, cause);
    }
}
