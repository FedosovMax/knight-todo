package com.knighttodo.todocore.rest.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CreateReminderRequestDto {

    private String name;
    private String message;
    private LocalDateTime reminderDate;
}
