package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
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

    @Override
    public TodoVO save(TodoVO todoVO) {

        Todo todo = todoGateway.save(todoMapper.toTodo(todoVO));

        return todoMapper.toTodoVO(todo); // need to add todoBlockId from requestEntity
    }

    @Override
    public List<TodoVO> findAll() {
        return todoGateway.findAll()
            .stream()
            .map(todoMapper::toTodoVO)
            .collect(Collectors.toList());
    }

    @Override
    public TodoVO findById(long todoId) {
        Optional<Todo> todos = todoGateway.findById(todoId);
        TodoVO todoVO;

        if (todos.isPresent()) {
            todoVO = todoMapper.toTodoVO(todos.get());
        } else {
            throw new RuntimeException("Did not find Todo id - " + todoId);
        }

        return todoVO;
    }

    @Override
    public TodoVO updateTodo(TodoVO changedTodoVO) {
        Todo changedTodo = todoMapper.toTodo(changedTodoVO);
        Todo todo = todoGateway.findById(changedTodo.getId()).orElseThrow(TodoNotFoundException::new);
        todo.setTodoName(changedTodo.getTodoName());
        todo.setTodoBlock(changedTodo.getTodoBlock());
        todo.setScaryness(changedTodo.getScaryness());
        todo.setHardness(changedTodo.getHardness());

        Todo updatedTodo = todoGateway.save(todo);
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
}

