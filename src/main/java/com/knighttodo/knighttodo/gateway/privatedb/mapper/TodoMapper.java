package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    @Mapping(target = "block", source = "blockVO")
    Todo toTodo(TodoVO todoVO);

    @Mapping(target = "blockVO", source = "block")
    TodoVO toTodoVO(Todo todo);
}
