package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.rest.request.TodoRequest;
import com.knighttodo.knighttodo.rest.response.TodoResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
//
//    @Query(value = "FROM Todo as t todo td WHERE todoBlock = 'firstBlock'")
//    List<Todo> findAllTodoByTodoBlockId();
//
//    @Query(value = "SELECT todo_name FROM todo", nativeQuery = true)
//    List<String> findAllStringTodo();

}
