package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoGatewayImpl implements TodoGateway {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public Todo save(TodoVO todoVO) {
        return todoRepository.save(todoMapper.toTodo(todoVO));
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<TodoVO> findById(long todoId) {
        return todoRepository.findById(todoId).map(todoMapper::toTodoVO);
    }

    @Override
    public void deleteById(long todoId) {
        todoRepository.deleteById(todoId);
    }
}
