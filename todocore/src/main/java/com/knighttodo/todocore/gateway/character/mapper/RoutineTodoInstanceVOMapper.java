package com.knighttodo.todocore.gateway.character.mapper;

import com.knighttodo.todocore.domain.RoutineTodoInstanceVO;
import com.knighttodo.todocore.gateway.character.request.ExperienceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoutineTodoInstanceVOMapper {

    @Mapping(target = "todoId", source = "id")
    @Mapping(target = "userId", ignore = true)
    ExperienceRequest routineTodoInstanceToExperienceRequest(RoutineTodoInstanceVO routineTodoInstanceVO);
}
