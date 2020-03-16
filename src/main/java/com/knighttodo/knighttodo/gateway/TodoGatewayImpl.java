package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<TodoVO> findById(String todoId) {
        return todoRepository.findById(todoId).map(todoMapper::toTodoVO);
    }

    @Override
    public void deleteById(String todoId) {
        todoRepository.deleteById(todoId);
    }

    @Override
    public List<TodoVO> findByTodoBlockId(String blockId) {
        return todoRepository.findByTodoBlockId(blockId).stream().map(todoMapper::toTodoVO)
            .collect(Collectors.toList());
    }
}
