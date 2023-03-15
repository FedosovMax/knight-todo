package com.knighttodo.todocore.rest.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class ReminderResponseDto {

    private UUID id;
    private String name;
    private String message;
    private LocalDateTime reminderDate;
}
