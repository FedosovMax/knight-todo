package com.knighttodo.todocore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoutineByDateNotFoundedException extends RuntimeException {

    public RoutineByDateNotFoundedException(String message, Throwable cause) {
        super(message, cause);
    }
}
