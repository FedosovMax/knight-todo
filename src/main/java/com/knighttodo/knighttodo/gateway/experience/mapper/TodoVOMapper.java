package com.knighttodo.knighttodo.gateway.experience.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.experience.request.TodoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoVOMapper {

    TodoRequest toTodoRequest(TodoVO todoVO);
}
