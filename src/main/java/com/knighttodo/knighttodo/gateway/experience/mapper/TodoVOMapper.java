package com.knighttodo.knighttodo.gateway.experience.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.experience.request.TodoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoVOMapper {

    @Mapping(target = "ready", source = "ready")
    @Mapping(target = "todoId", source = "id")
    @Mapping(target = "userId", ignore = true)
    TodoRequest toTodoRequest(TodoVO todoVO);
}
