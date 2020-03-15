package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    Todo toTodo(TodoVO todoVO);

    TodoVO toTodoVO(Todo todo);

    default Optional<TodoVO> toOptionalTodoVO(Optional<Todo> optionalTodo) {
        if(optionalTodo.isPresent()) {
            return Optional.of(toTodoVO(optionalTodo.get()));
        }
        return Optional.empty();
    }
}
