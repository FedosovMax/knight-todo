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
    public TodoVO save(Todo todo) {
        return todoRepository.save(todoMapper.toTodoVO(todo));
    }

    @Override
    public List<TodoVO> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<TodoVO> findById(long todoId) {
        return todoRepository.findById(todoId);
    }

    @Override
    public void deleteById(long todoId) {
        todoRepository.deleteById(todoId);
    }
}
