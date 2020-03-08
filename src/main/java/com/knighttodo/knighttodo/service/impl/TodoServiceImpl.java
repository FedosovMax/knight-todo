package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
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

        Todo todo = todoGateway.save(todoMapper.toTodo(todoVO));

        return todoMapper.toTodoVO(todo); // need to add todoBlockId from requestEntity
    }

    @Override
    public List<TodoVO> findAll() {

        List<Todo> todos = todoGateway.findAll();

        List<TodoVO> todoVOS = new ArrayList<>();

        for (int i = 0; i < todos.size(); i++) {
            todoVOS.add(todoMapper.toTodoVO(todos.get(i)));
        }

        return todoVOS;
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

        final Todo todo = this.todoGateway.findById(todoMapper.toTodo(changedTodoVO).getId())
                .orElseThrow(TodoNotFoundException::new);

        TodoVO todoVO = new TodoVO();

        todoVO.setId(todo.getId());
        todoVO.setTodoName(todo.getTodoName());
        todoVO.setTodoBlock(todo.getTodoBlock());


        todoGateway.save(todoMapper.toTodo(changedTodoVO));

        return todoVO;
    }

    @Override
    public void deleteById(long todoId) {
        todoGateway.deleteById(todoId);
    }


    @Override
    public List<TodoVO> getAllTodoByBlockId(long blockId) {

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

