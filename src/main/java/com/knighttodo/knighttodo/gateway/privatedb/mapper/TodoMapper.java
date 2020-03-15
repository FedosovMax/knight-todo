package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    Todo toTodo(TodoVO todoVO);

    TodoVO toTodoVO(Todo todo);
}
