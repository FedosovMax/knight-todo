package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoutineTodoRepository extends JpaRepository<RoutineTodo, UUID> {

    List<RoutineTodo> findByRoutineInstanceId(UUID id);
}
