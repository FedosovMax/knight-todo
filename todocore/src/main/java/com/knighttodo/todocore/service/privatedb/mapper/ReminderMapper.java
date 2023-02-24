package com.knighttodo.todocore.service.privatedb.mapper;

import com.knighttodo.todocore.domain.ReminderVO;
import com.knighttodo.todocore.service.privatedb.representation.Reminder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReminderMapper {

    Reminder toReminder(ReminderVO reminderVO);

    ReminderVO toReminderVO(Reminder reminder);
}
