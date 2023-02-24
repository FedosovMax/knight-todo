package com.knighttodo.todocore.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ReminderNotFoundException extends RuntimeException {

    public ReminderNotFoundException(UUID id) {
        log.warn("Reminder with id: {} not found", id);
    }
}
