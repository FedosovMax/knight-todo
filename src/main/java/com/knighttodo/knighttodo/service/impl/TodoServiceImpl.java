package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
import com.knighttodo.knighttodo.service.TodoService;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoGateway todoGateway;

    @Override
    public TodoVO save(TodoVO todoVO) {
        return todoGateway.save(todoVO);
    }

    @Override
    public List<TodoVO> findAll() {
        return todoGateway.findAll();
    }

    @Override
    public TodoVO findById(long id) {
        Optional<TodoVO> result = todoGateway.findById(id);

        if (result.isPresent()) {
            return result.get();
        }
        throw new RuntimeException("Did not find Todo id - " + id);
    }

    @Override
    public TodoVO updateTodo(TodoVO changedTodoVO) {
        TodoVO todoVO = todoGateway.findById(changedTodoVO.getId()).orElseThrow(TodoNotFoundException::new);

        todoVO.setTodoName(changedTodoVO.getTodoName());
        todoVO.setTodoBlock(changedTodoVO.getTodoBlock());
        todoVO.setScaryness(changedTodoVO.getScaryness());
        todoVO.setHardness(changedTodoVO.getHardness());
        return todoGateway.save(todoVO);
    }

    @Override
    public void deleteById(long id) {
        todoGateway.deleteById(id);
    }

    @Override
    public List<TodoVO> findByBlockId(long id) {
        return todoGateway.findByTodoBlockId(id);
    }
}

