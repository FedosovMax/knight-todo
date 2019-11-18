package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;

import com.knighttodo.knighttodo.rest.response.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoGatewayImpl implements TodoGateway {

    private final TodoRepository todoRepository;


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
        return todoRepository.findById(todoId);
    }

    @Override
    public void deleteById(long todoId) {
        todoRepository.deleteById(todoId);
    }
}
