package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.rest.request.TodoRequest;
import com.knighttodo.knighttodo.rest.response.TodoResponse;
import com.knighttodo.knighttodo.service.TodoBlockService;
import com.knighttodo.knighttodo.service.TodoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoGateway todoGateway;
    private final TodoBlockService todoBlockService;
    private final TodoMapper todoMapper;

    @Override
    public TodoVO save(TodoVO todoVO) {
        return todoGateway.save(todoMapper.toTodo(todoVO)); // need to add todoBlockId from requestEntity
    }

    @Override
    public List<TodoVO> findAll() {

        List<TodoVO> todos = todoGateway.findAll();

        return todos;
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

        final TodoVO todoVO = this.todoGateway.findById(changedTodoVO.getId())
                .orElseThrow(TodoNotFoundException::new);

        todoVO.setId(changedTodoVO.getId());
        todoVO.setTodoName(changedTodoVO.getTodoName());
        todoVO.setTodoBlock(changedTodoVO.getTodoBlock());


        todoGateway.save(todoMapper.toTodo(changedTodoVO));

        return todoVO;
    }

    @Override
    public void deleteById(long todoId) {
        todoGateway.deleteById(todoId);
    }


    @Override
    public List<TodoVO> getAllTodoByBlockId(long blockId) {

        List<TodoVO> beforeTodos = todoGateway.findAll();

        List<TodoVO> resultTodos = new ArrayList<>();
        for (TodoVO beforeTodo : beforeTodos) {
            if (beforeTodo.getTodoBlock().getId() == blockId) {
                resultTodos.add(beforeTodo);
            }
        }

        return resultTodos;
    }



}

