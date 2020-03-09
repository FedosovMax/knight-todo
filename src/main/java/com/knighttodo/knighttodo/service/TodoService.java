package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.TodoVO;

import java.util.List;

public interface TodoService {

    TodoVO save(TodoVO todoVO);

    List<TodoVO> findAll();

    TodoVO findById(long todoId);

    TodoVO updateTodo(TodoVO changedTodoVO);

    void deleteById(long TodoId);

    List<TodoVO> getAllTodoByBlockId(long blockId);
}
