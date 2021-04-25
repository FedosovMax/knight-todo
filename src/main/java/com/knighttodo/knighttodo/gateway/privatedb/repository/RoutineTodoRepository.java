package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineTodoRepository extends JpaRepository<RoutineTodo, String> {

    List<RoutineTodo> findByRoutineId(String routineId);
}
