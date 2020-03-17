package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
import com.knighttodo.knighttodo.gateway.experience.ExperienceGateway;
import com.knighttodo.knighttodo.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoGateway todoGateway;
    private final ExperienceGateway experienceGateway;

    @Override
    public TodoVO save(String blockId, TodoVO todoVO) {
        todoVO.setTodoBlockId(blockId);
        return todoGateway.save(todoVO);
    }

    @Override
    public List<TodoVO> findAll() {
        return todoGateway.findAll();
    }

    @Override
    public TodoVO findById(String todoId) {
        return todoGateway.findById(todoId)
            .orElseThrow(() -> new TodoNotFoundException(String.format("Todo with such id:%s can't be found", todoId)));
    }

    @Override
    public TodoVO updateTodo(String todoId, TodoVO changedTodoVO) {
        TodoVO todoVO = todoGateway.findById(todoId)
            .orElseThrow(() -> new TodoNotFoundException(String.format("Todo with such id:%s can't be found", todoId)));

        todoVO.setTodoName(changedTodoVO.getTodoName());
        todoVO.setTodoBlockId(changedTodoVO.getTodoBlockId());
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
    public void updateIsReady(String blockId, String todoId, boolean isReady) {
        TodoVO todoVO = findById(todoId);
        todoVO.setTodoBlockId(blockId);
        todoVO.setReady(isReady);
        experienceGateway.calculateExperience(todoVO);
        todoGateway.save(todoVO);

    }
}

