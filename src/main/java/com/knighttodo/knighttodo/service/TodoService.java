package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.rest.request.TodoRequest;
import com.knighttodo.knighttodo.rest.response.TodoResponse;

import java.util.List;

public interface TodoService {

    TodoResponse save(TodoRequest todoRequset);

    List<TodoResponse> findAll();

    TodoResponse findById(long todoId);

    TodoResponse updateTodo(TodoRequest changedTodoRequest);

    void deleteById(long TodoId);

    List<TodoResponse> getAllTodoByBlockId(long blockId);

}
