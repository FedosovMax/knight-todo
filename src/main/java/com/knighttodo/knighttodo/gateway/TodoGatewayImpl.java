package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoGatewayImpl implements TodoGateway {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public TodoVO save(TodoVO todoVO) {
        Todo savedTodo = todoRepository.save(todoMapper.toTodo(todoVO));
        return todoMapper.toTodoVO(savedTodo);
    }

    @Override
    public List<TodoVO> findAll() {
        return todoRepository.findAll().stream().map(todoMapper::toTodoVO).collect(Collectors.toList());
    }

    @Override
    public Optional<TodoVO> findById(long id) {
        return todoMapper.toOptionalTodoVO(todoRepository.findById(id));
    }

    @Override
    public void deleteById(long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public List<TodoVO> findByTodoBlockId(long id) {
        return todoRepository.findByTodoBlockId(id).stream().map(todoMapper::toTodoVO).collect(Collectors.toList());
    }
}
