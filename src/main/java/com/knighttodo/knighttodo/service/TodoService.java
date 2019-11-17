package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;

import java.util.Collection;
import java.util.List;

public interface TodoService {

    void save(Todo Todo);

    List<Todo> findAll();

    Todo findById(long todoId);

    Todo updateTodo(Todo changedTodo);

    void deleteById(long TodoId);

    List<Todo> getAllTodoByBlockId(long blockId);

    List<String> getAllStringTodo();
}
