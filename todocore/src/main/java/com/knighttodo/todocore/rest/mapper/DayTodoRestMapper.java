package com.knighttodo.todocore.rest.mapper;

import com.knighttodo.todocore.domain.DayTodoVO;
import com.knighttodo.todocore.rest.request.DayTodoRequestDto;
import com.knighttodo.todocore.rest.response.DayTodoReadyResponseDto;
import com.knighttodo.todocore.rest.response.DayTodoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DayTodoRestMapper {

    @Mapping(target = "dayId", source = "day.id")
    DayTodoResponseDto toDayTodoResponseDto(DayTodoVO dayTodoVO);

    DayTodoVO toDayTodoVO(DayTodoRequestDto requestDto);

    @Named("fromIdToDayTodoVOWithId")
    @Mapping(target = "id", source = "dayTodoId")
    DayTodoVO toDayTodoVO(UUID dayTodoId);

    @Mapping(target = "dayId", source = "day.id")
    DayTodoReadyResponseDto toDayTodoReadyResponseDto(DayTodoVO dayTodoVO);
}
