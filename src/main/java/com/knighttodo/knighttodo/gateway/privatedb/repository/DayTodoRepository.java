package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.DayTodo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayTodoRepository extends JpaRepository<DayTodo, String> {

    List<DayTodo> findByDayId(String dayId);
}
