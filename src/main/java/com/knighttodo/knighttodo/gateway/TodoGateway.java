package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoGateway {

    TodoVO save(Todo todo);

    List<TodoVO> findAll();

    Optional<TodoVO> findById(long todoId);

    void deleteById(long todoId);
}
