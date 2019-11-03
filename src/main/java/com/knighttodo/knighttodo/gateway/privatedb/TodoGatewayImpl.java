package com.knighttodo.knighttodo.gateway.privatedb;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoGatewayImpl implements TodoGateway {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public TodoVO save(TodoVO todoVO) {

        Todo todo = todoMapper.toTodo(todoVO);
        return todoMapper.toTodoVO(todoRepository.save(todo));
    }
}
