package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.entity.TodoBlock;

import java.util.List;

public interface TodoBlockService {

    void save(TodoBlock todoBlock);

    List<TodoBlock> findAll();

    TodoBlock findById(long todoBlockId);

    TodoBlock updateTodoBlock(TodoBlock changedTodoBlock);

    void deleteById(long TodoBlockId);
}