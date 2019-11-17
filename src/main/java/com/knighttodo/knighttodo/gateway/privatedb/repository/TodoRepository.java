package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query(value = "SELECT todo_list FROM todo_block WHERE block_name = 'firstBlock'", nativeQuery = true)
    List<Todo> findAllTodoByTodoBlockId();

    @Query(value = "SELECT todo_name FROM todo", nativeQuery = true)
    List<String> findAllStringTodo();

}
