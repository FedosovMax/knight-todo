package com.knighttodo.knighttodo.rest.request.mapper;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.rest.request.DayTodoRequestDto;
import com.knighttodo.knighttodo.rest.response.DayTodoReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.DayTodoResponseDto;
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
