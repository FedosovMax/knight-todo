package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import java.util.List;

public interface TodoBlockService {

    TodoBlockVO save(TodoBlockVO todoBlockVO);

    List<TodoBlockVO> findAll();

    TodoBlockVO findById(String blockId);

    TodoBlockVO updateTodoBlock(String blockId, TodoBlockVO changedTodoBlockVO);

    void deleteById(String blockId);
}
