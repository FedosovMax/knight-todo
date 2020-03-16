package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    @Mapping(source = "ready", target = "ready")
    Todo toTodo(TodoVO todoVO);

    @Mapping(source = "ready", target = "ready")
    TodoVO toTodoVO(Todo todo);
}
