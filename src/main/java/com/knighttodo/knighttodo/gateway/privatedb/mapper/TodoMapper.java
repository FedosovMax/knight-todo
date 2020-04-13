package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    @Mapping(target = "todoBlock", source = "todoBlockVO")
    Todo toTodo(TodoVO todoVO);

    @Mapping(target = "todoBlockVO", source = "todoBlock")
    TodoVO toTodoVO(Todo todo);
}
