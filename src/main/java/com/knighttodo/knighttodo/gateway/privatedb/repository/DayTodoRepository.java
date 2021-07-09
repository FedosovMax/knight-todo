package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.DayTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DayTodoRepository extends JpaRepository<DayTodo, UUID> {

    List<DayTodo> findByDayId(UUID dayId);
}
