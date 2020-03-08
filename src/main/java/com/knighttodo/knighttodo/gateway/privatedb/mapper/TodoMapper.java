package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.rest.request.TodoRequest;
import com.knighttodo.knighttodo.rest.response.TodoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    Todo toTodo(TodoVO todoVO);

    TodoVO toTodoVO(Todo todo);

    TodoVO toTodoVO(TodoRequest todoRequest);

    TodoResponse todoResponseFromTodoVO(TodoVO todoVO);
}
