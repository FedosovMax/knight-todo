package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoVO;

import java.util.List;
import java.util.Optional;

public interface TodoGateway {

    TodoVO save(TodoVO todoVO);

    List<TodoVO> findAll();

    Optional<TodoVO> findById(long id);

    void deleteById(long id);

    List<TodoVO> findByTodoBlockId(long id);
}
