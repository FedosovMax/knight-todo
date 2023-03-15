package com.knighttodo.todocore.rest.mapper;

import com.knighttodo.todocore.domain.ReminderVO;
import com.knighttodo.todocore.rest.request.CreateReminderRequestDto;
import com.knighttodo.todocore.rest.request.UpdateReminderRequestDto;
import com.knighttodo.todocore.rest.response.ReminderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReminderRestMapper {

    @Mapping(target = "id", ignore = true)
    ReminderVO toReminderVO(CreateReminderRequestDto requestDto);

    ReminderResponseDto toReminderResponseDto(ReminderVO savedReminderVO);

    @Mapping(target = "id", ignore = true)
    ReminderVO toReminderVO(UpdateReminderRequestDto requestDto);
}
