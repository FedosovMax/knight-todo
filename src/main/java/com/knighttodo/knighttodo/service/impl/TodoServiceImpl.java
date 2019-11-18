package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
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

    @Override
    public TodoResponse save(TodoRequest todoRequest) {

        Todo todo = todoRequestToTodo(todoRequest);

        return todoToTodoResponse(todoGateway.save(todo)); // need to add todoBlockId from requestEntity
    }

    @Override
    public List<TodoResponse> findAll() {

        List<Todo> todos = todoGateway.findAll();

        List<TodoResponse> todoResponses = new ArrayList<>();

        for (Todo todo : todos) {
            todoToTodoResponse(todo);
            todoResponses.add(todoToTodoResponse(todo));
        }

        return todoResponses;
    }

    @Override
    public TodoResponse findById(long todoId) {
        Optional<Todo> todos = todoGateway.findById(todoId);

        TodoResponse todoResponse;

        if (todos.isPresent()) {
            todoResponse = todoToTodoResponse(todos.get());
        } else {
            throw new RuntimeException("Did not find Todo id - " + todoId);
        }

        return todoResponse;
    }

    @Override
    public TodoResponse updateTodo(TodoRequest changedTodoRequest) {

        final TodoResponse todoResponse = todoToTodoResponse(this.todoGateway.findById(changedTodoRequest.getId())
                .orElseThrow(TodoNotFoundException::new));

        todoResponse.setId(changedTodoRequest.getId());
        todoResponse.setTodoName(changedTodoRequest.getTodoName());
        todoResponse.setTodoBlock(changedTodoRequest.getTodoBlock());

        Todo todo = todoRequestToTodo(changedTodoRequest);

        todoToTodoResponse(todoGateway.save(todo));

        return todoResponse;
    }

    @Override
    public void deleteById(long todoId) {
        todoGateway.deleteById(todoId);
    }


    @Override
    public List<TodoResponse> getAllTodoByBlockId(long blockId) {

        List<Todo> beforeTodos = todoGateway.findAll();

        List<TodoResponse> todosRespons = new ArrayList<>();
        for (Todo beforeTodo : beforeTodos) {
            if (beforeTodo.getTodoBlock().getId() == blockId) {
                todosRespons.add(todoToTodoResponse(beforeTodo));
            }
        }

        return todosRespons;
    }

    private Todo todoRequestToTodo(TodoRequest todoRequest){
        Todo todo = new Todo();

        todo.setId(todoRequest.getId());
        todo.setTodoName(todoRequest.getTodoName());
        todo.setScaryness(todoRequest.getScaryness());
        todo.setHardness(todoRequest.getHardness());
        todo.setReady(todoRequest.isReady());
        todo.setTodoBlock(todoRequest.getTodoBlock());

        return todo;
    }

    private TodoResponse todoToTodoResponse(Todo todo){
        TodoResponse todoResponse = new TodoResponse();

        todoResponse.setId(todo.getId());
        todoResponse.setTodoName(todo.getTodoName());
        todoResponse.setScaryness(todo.getScaryness());
        todoResponse.setHardness(todo.getHardness());
        todoResponse.setReady(todo.isReady());
        todoResponse.setTodoBlock(todo.getTodoBlock());

        return todoResponse;
    }


}

