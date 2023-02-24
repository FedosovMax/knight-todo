package com.knighttodo.todocore.factories;

import com.knighttodo.todocore.rest.request.CreateReminderRequestDto;
import com.knighttodo.todocore.rest.request.UpdateReminderRequestDto;

import java.time.LocalDateTime;

public class ReminderFactory {

    public static final String NAME_CREATE = "Name to create";
    public static final String MESSAGE_CREATE = "Message to create";

    public static final String NAME_UPDATE = "Name to update";
    public static final String MESSAGE_UPDATE = "Message to update";

    public static CreateReminderRequestDto getCreateReminderRequestDto() {
        return new CreateReminderRequestDto()
                .setName(NAME_CREATE)
                .setMessage(MESSAGE_CREATE)
                .setReminderDate(LocalDateTime.now().plusDays(2));
    }

    public static UpdateReminderRequestDto getUpdateReminderRequestDto() {
        return new UpdateReminderRequestDto()
                .setName(NAME_UPDATE)
                .setMessage(MESSAGE_UPDATE)
                .setReminderDate(LocalDateTime.now().plusDays(2));
    }
}
