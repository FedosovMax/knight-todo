package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
import com.knighttodo.knighttodo.gateway.experience.ExperienceGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;

import com.knighttodo.knighttodo.service.TodoService;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoGateway todoGateway;
    private final TodoMapper todoMapper;
    private final ExperienceGateway experienceGateway;

    @Override
    public TodoVO save(TodoVO todoVO) {
        return todoGateway.save(todoVO);
    }

    @Override
    public List<TodoVO> findAll() {
        return todoGateway.findAll();
    }

    @Override
    public TodoVO findById(String todoId) {
        Optional<TodoVO> result = todoGateway.findById(todoId);

        if (result.isPresent()) {
            return result.get();
        }
        throw new RuntimeException("Did not find Todo id - " + todoId);
    }

    @Override
    public TodoVO updateTodo(TodoVO changedTodoVO) {
        TodoVO todoVO = todoGateway.findById(changedTodoVO.getId())
            .orElseThrow(() -> new TodoNotFoundException("Can't find todo with such id"));

        todoVO.setTodoName(changedTodoVO.getTodoName());
        todoVO.setTodoBlock(changedTodoVO.getTodoBlock());
        todoVO.setScaryness(changedTodoVO.getScaryness());
        todoVO.setHardness(changedTodoVO.getHardness());
        return todoGateway.save(todoVO);
    }

    @Override
    public void deleteById(String todoId) {
        todoGateway.deleteById(todoId);
    }

    @Override
    public List<TodoVO> findByBlockId(String blockId) {
        return todoGateway.findByTodoBlockId(blockId);
    }

    @Override
    public void updateIsReady(long todoId, boolean isReady) {
        TodoVO todoVO = findById(todoId);
        todoVO.setReady(isReady);
        todoGateway.save(todoVO);
        experienceGateway.calculateExperience(todoVO);
    }
}

