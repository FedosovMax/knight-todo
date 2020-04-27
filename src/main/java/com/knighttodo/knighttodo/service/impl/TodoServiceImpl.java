package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.exception.UnchangeableFieldUpdateException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
import com.knighttodo.knighttodo.gateway.experience.ExperienceGateway;
import com.knighttodo.knighttodo.service.BlockService;
import com.knighttodo.knighttodo.service.TodoService;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final BlockService blockService;
    private final TodoGateway todoGateway;
    private final ExperienceGateway experienceGateway;

    @Override
    public TodoVO save(String blockId, TodoVO todoVO) {
        todoVO.setBlock(blockService.findById(blockId));
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

        checkUpdatePossibility(todoVO, changedTodoVO);

        todoVO.setTodoName(changedTodoVO.getTodoName());
        todoVO.setScariness(changedTodoVO.getScariness());
        todoVO.setHardness(changedTodoVO.getHardness());
        return todoGateway.save(todoVO);
    }

    private void checkUpdatePossibility(TodoVO todoVO, TodoVO changedTodoVO) {
        if (todoVO.isReady() && isAtLeastOneUnchangeableFieldUpdated(todoVO, changedTodoVO)) {
            throw new UnchangeableFieldUpdateException("Can not update todo's field in ready state");
        }
    }

    private boolean isAtLeastOneUnchangeableFieldUpdated(TodoVO todoVO, TodoVO changedTodoVO) {
        return !todoVO.getScariness().equals(changedTodoVO.getScariness())
            || !todoVO.getHardness().equals(changedTodoVO.getHardness());
    }

    @Override
    public void deleteById(String todoId) {
        todoGateway.deleteById(todoId);
    }

    @Override
    public List<TodoVO> findByBlockId(String blockId) {
        return todoGateway.findByBlockId(blockId);
    }

    @Override
    public TodoVO updateIsReady(String blockId, String todoId, boolean isReady) {
        TodoVO todoVO = findById(todoId);
        todoVO.setBlock(blockService.findById(blockId));
        todoVO.setReady(isReady);
        todoVO = todoGateway.save(todoVO);
        return experienceGateway.calculateExperience(todoVO);
    }
}

