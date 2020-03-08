package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoGatewayImpl implements TodoGateway {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public Todo save(Todo todo) {

        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> findAll() {

        return todoRepository.findAll();
    }

    @Override
    public Optional<Todo> findById(long todoId) {

        Optional<Todo> todo = todoRepository.findById(todoId);

        return todo;
    }

    @Override
    public void deleteById(long todoId) {
        todoRepository.deleteById(todoId);
    }
}
