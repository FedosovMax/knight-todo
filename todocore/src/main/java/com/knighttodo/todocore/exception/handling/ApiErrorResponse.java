package com.knighttodo.todocore.exception.handling;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApiErrorResponse implements Serializable {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    public ApiErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
