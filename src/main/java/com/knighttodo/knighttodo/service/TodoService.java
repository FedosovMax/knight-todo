package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.TodoVO;

import java.util.List;

public interface TodoService {

    TodoVO save(TodoVO todoVO);

    List<TodoVO> findAll();

    TodoVO findById(String todoId);

    TodoVO updateTodo(TodoVO changedTodoVO);

    void deleteById(String todoId);

    List<TodoVO> findByBlockId(String blockId);
}
