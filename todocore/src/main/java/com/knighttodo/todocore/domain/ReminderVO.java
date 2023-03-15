package com.knighttodo.todocore.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ReminderVO {

    private UUID id;
    private String name;
    private String message;
    private LocalDateTime reminderDate;
}
