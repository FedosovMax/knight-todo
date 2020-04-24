package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.TodoVO;

import java.util.List;

public interface TodoService {

    TodoVO save(String blockId, TodoVO todoVO);

    List<TodoVO> findAll();

    TodoVO findById(String todoId);

    TodoVO updateTodo(String todoId, TodoVO changedTodoVO);

    void deleteById(String todoId);

    List<TodoVO> findByBlockId(String blockId);

    TodoVO updateIsReady(String blockId, String todoId, boolean isReady);
}
