package com.knighttodo.todocore.gateway.character.mapper;

import com.knighttodo.todocore.domain.RoutineTodoVO;
import com.knighttodo.todocore.gateway.character.request.ExperienceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoutineTodoVOMapper {

    @Mapping(target = "todoId", source = "id")
    @Mapping(target = "userId", ignore = true)
    ExperienceRequest routineTodoToExperienceRequest(RoutineTodoVO routineTodoVO);
}
