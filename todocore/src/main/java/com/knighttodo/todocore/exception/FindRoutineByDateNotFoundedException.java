package com.knighttodo.todocore.exception;

public class FindRoutineByDateNotFoundedException extends RuntimeException {
    public FindRoutineByDateNotFoundedException(String message, RuntimeException ex) {
        super(message, ex);
    }
}
