package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("TodoMapper")
@Mapper(componentModel = "spring", uses = {RoutineMapper.class, BlockMapper.class})
public interface TodoMapper {

    @Mapping(target = "routine", qualifiedByName = {"RoutineMapper", "toRoutineWithoutBlock"})
    Todo toTodo(TodoVO todoVO);

    @Named("toTodos")
    @IterableMapping(qualifiedByName = "toTodoWithoutRoutineAndBlock")
    List<Todo> toTodos(List<TodoVO> todoVOs);

    @Mapping(target = "routine", qualifiedByName = {"RoutineMapper", "toRoutineVOWithoutBlock"})
    TodoVO toTodoVO(Todo todo);

    @Named("toTodoVOs")
    @IterableMapping(qualifiedByName = "toTodoVOWithoutRoutineAndBlock")
    List<TodoVO> toTodoVOs(List<Todo> todos);

    @Named("toTodoWithoutRoutineAndBlock")
    @Mapping(target = "routine", ignore = true)
    @Mapping(target = "block", ignore = true)
    Todo toTodoWithoutRoutine(TodoVO todoVO);

    @Named("toTodoVOWithoutRoutineAndBlock")
    @Mapping(target = "block", ignore = true)
    @Mapping(target = "routine", ignore = true)
    TodoVO toTodoVOWithoutRoutine(Todo todo);
}
