package com.knighttodo.todocore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DateBadFormatException extends RuntimeException {

    public DateBadFormatException(String message) { super(message); }
}
