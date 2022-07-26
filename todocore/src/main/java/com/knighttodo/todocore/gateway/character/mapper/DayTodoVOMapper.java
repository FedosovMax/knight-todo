package com.knighttodo.todocore.gateway.character.mapper;

import com.knighttodo.todocore.domain.DayTodoVO;
import com.knighttodo.todocore.gateway.character.request.ExperienceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DayTodoVOMapper {

    @Mapping(target = "todoId", source = "id")
    @Mapping(target = "userId", ignore = true)
    ExperienceRequest DayTodoToExperienceRequest(DayTodoVO dayTodoVO);
}
