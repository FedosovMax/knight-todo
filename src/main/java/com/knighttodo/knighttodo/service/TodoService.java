package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.rest.request.TodoRequest;
import com.knighttodo.knighttodo.rest.response.TodoResponse;

import java.util.List;

public interface TodoService {

    TodoVO save(TodoVO todoVO);

    List<TodoVO> findAll();

    TodoVO findById(long todoId);

    TodoVO updateTodo(TodoVO changedTodoVO);

    void deleteById(long TodoId);

    List<TodoVO> getAllTodoByBlockId(long blockId);

}
