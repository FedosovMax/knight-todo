package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
import com.knighttodo.knighttodo.gateway.experience.ExperienceGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.service.TodoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Todo todo = todoGateway.save(todoVO);

        return todoMapper.toTodoVO(todo);
    }

    @Override
    public List<TodoVO> findAll() {
        return todoGateway.findAll().stream().map(todoMapper::toTodoVO).collect(Collectors.toList());
    }

    @Override
    public TodoVO findById(long todoId) {
        Optional<TodoVO> todos = todoGateway.findById(todoId);
        TodoVO todoVO;

        if (todos.isPresent()) {
            todoVO = todos.get();
        } else {
            throw new RuntimeException("Did not find Todo id - " + todoId);
        }

        return todoVO;
    }

    @Override
    public TodoVO updateTodo(TodoVO changedTodoVO) {
        Todo changedTodo = todoMapper.toTodo(changedTodoVO);
        TodoVO todoVO = todoGateway.findById(changedTodo.getId())
            .orElseThrow(() -> new TodoNotFoundException("Can't find todo with such id"));
        todoVO.setTodoName(changedTodo.getTodoName());
        todoVO.setTodoBlock(changedTodo.getTodoBlock());
        todoVO.setScaryness(changedTodo.getScaryness());
        todoVO.setHardness(changedTodo.getHardness());

        Todo updatedTodo = todoGateway.save(todoVO);
        return todoMapper.toTodoVO(updatedTodo);
    }

    @Override
    public void deleteById(long todoId) {
        todoGateway.deleteById(todoId);
    }


    @Override
    public List<TodoVO> findByBlockId(long blockId) {

        List<Todo> beforeTodos = todoGateway.findAll();

        List<TodoVO> resultTodos = new ArrayList<>();

        for (Todo beforeTodo : beforeTodos) {
            if (beforeTodo.getTodoBlock().getId() == blockId) {
                resultTodos.add(todoMapper.toTodoVO(beforeTodo));
            }
        }

        return resultTodos;
    }

    @Override
    public void updateIsReady(long todoId, boolean isReady) {
        TodoVO todoVO = findById(todoId);
        todoVO.setReady(isReady);
        todoGateway.save(todoVO);
        experienceGateway.calculateExperience(todoVO);
    }
}

