package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findById(String todoId);

    void deleteById(String todoId);

    List<Todo> findByTodoBlockId(String blockId);
}
