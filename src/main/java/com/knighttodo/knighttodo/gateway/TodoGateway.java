package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoVO;

import java.util.List;
import java.util.Optional;

public interface TodoGateway {

    TodoVO save(TodoVO todoVO);

    List<TodoVO> findAll();

    Optional<TodoVO> findById(String todoId);

    void deleteById(String todoId);

    List<TodoVO> findByBlockId(String blockId);
}
