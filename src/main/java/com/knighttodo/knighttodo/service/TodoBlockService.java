package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import java.util.List;

public interface TodoBlockService {

    TodoBlockVO save(TodoBlockVO todoBlockVO);

    List<TodoBlockVO> findAll();

    TodoBlockVO findById(long blockId);

    TodoBlockVO updateTodoBlock(TodoBlockVO changedTodoBlockVO);

    void deleteById(long blockId);
}
